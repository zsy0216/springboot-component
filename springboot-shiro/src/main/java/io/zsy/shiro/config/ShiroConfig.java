package io.zsy.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
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
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 9:51
 */
@Slf4j
@Configuration
public class ShiroConfig {
    // ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterBean = new ShiroFilterFactoryBean();
        // 关联 DefaultWebSecurityManager
        shiroFilterBean.setSecurityManager(securityManager);

        // 添加 shiro 的内置过滤器
        /*
         anon: 无需认证即可访问
         authc: 必须认证之后才能访问
         user: 必须拥有记住我功能才能访问
         perms: 拥有某个资源权限才能访问
         role: 拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        // filterMap.put("/user/*", "authc");
        // filterMap.put("/user/add", "authc");
        filterMap.put("/user/update", "authc");

        // 给请求设置权限
        filterMap.put("/user/add", "perms[user:add]");

        shiroFilterBean.setFilterChainDefinitionMap(filterMap);

        // 设置登录请求地址
        shiroFilterBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterBean.setSuccessUrl("/index");
        //未授权请求;
        shiroFilterBean.setUnauthorizedUrl("/login");
        return shiroFilterBean;
    }

    // DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") Realm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联 realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 创建 Realm 对象, 需要实现Realm类型的类
    @Bean
    public Realm userRealm() {
        return new AuthorizingRealm() {
            // 认证
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
                log.info("执行认证~~~");
                // 伪造用户数据
                String username = "root";
                String password = "123456";
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                if (!username.equals(token.getUsername())) {
                    return null; // 抛出异常 UnknownAccountException
                }
                // shiro 进行密码认证
                return new SimpleAuthenticationInfo("", password, "");
            }

            // 授权
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
                log.info("执行授权~~~");
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                info.addStringPermission("user:add");
                // 授权时取出
                Subject subject = SecurityUtils.getSubject();
                // User user = (User) subject.getPrincipal();
                // 设置权限
                // info.setStringPermissions();
                return info;
            }
        };
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return ShiroDialect
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
