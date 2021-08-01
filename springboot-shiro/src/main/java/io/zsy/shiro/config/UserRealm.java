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

import java.util.List;
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
        // 使用父方法使匹配方式生效
        setCredentialsMatcher(matcher);
    }

    /**
     * 授权
     *
     * @param principals 用户凭证信息，包装了doGetAuthenticationInfo 方法返回对象的第一个参数
     *                   可以通过 getPrimaryPrincipal() 得到他
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("执行授权~~~");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取登陆用户信息
        SysUser user = (SysUser) principals.getPrimaryPrincipal();

        // 从数据库查询角色和权限
        List<String> roles = userService.selectRoles(user);
        List<String> permissions = userService.selectPermissions(user);

        // 构建角色权限校验
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken 用户输入的认证信息
     * @return AuthenticationInfo 传递 principal(用户信息) credential(密码) 盐值等信息用于密码认证
     * @throws AuthenticationException e
     */
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
        // shiro 进行密码认证, 第一个参数 principal(缓存对象)在授权时可以从 principals 集合中得到
        // return new SimpleAuthenticationInfo(user, user.getPassword(), "");
        /*
            传递账号和密码:
            参数1：缓存对象, 授权时可以通过参数PrincipalCollection得到
            参数2：明文密码, 用户输入的密码
            参数3：字节salt, 加密盐值
            参数4：当前DefinitionRealm名称
         */
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }
}
