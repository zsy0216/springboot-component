package io.zsy.shiro.config;

import io.zsy.shiro.model.SysUser;
import io.zsy.shiro.service.SysUserService;
import io.zsy.shiro.util.DigestsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:45
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    SysUserService userService;

    /**
     * 构造函数，指定密码匹配方式等
     */
    public UserRealm() {
        // 指定密码匹配方式为sha1
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(DigestsUtil.SHA1);
        // 指定密码迭代次数
        matcher.setHashIterations(DigestsUtil.ITERATIONS);
        // 使用父亲方法使匹配方式生效
        setCredentialsMatcher(matcher);
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行授权~~~");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // info.addStringPermission("user:add");

        // 获取登陆用户信息
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        // 设置权限
        // info.addStringPermission(user.getPerms());
        info.addStringPermission("user:add");
        // 设置多个权限 info.setStringPermissions();
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("执行认证~~~");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 查询数据库
        SysUser user = userService.selectByUsername(token.getUsername());
        // 判断用户名
        if (Objects.isNull(user)) {
            // 如果返回 AuthenticationInfo 为空 会抛出异常 UnknownAccountException，表示用户名信息错误
            return null;
        }
        // shiro 进行密码认证, 第一个参数 principal(缓存对象)授权时可以从 Subject 对象中得到
        // return new SimpleAuthenticationInfo(user, user.getPassword(), "");
        /*
            传递账号和密码:
            参数1：缓存对象，
            参数2：明文密码，
            参数3：字节salt,
            参数4：当前DefinitionRealm名称
         */
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }
}
