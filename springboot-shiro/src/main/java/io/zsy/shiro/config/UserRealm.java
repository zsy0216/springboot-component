package io.zsy.shiro.config;

import io.zsy.shiro.model.User;
import io.zsy.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:45
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行授权~~~");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // info.addStringPermission("user:add");

        // 获取登陆用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        // 设置权限
        info.addStringPermission(user.getPerms());
        // 设置多个权限 info.setStringPermissions();
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("执行认证~~~");
        // 伪造用户数据
        // String username = "root";
        // String password = "123456";
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 查询数据库
        User user = userService.selectByUsername(token.getUsername());
        // 判断用户名
        if (Objects.isNull(user)) {
            return null; // 抛出异常 UnknownAccountException
        }
        // shiro 进行密码认证
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}