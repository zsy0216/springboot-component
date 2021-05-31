package io.zsy.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
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

        // filterMap.put("/user/add", "authc");
        // filterMap.put("/user/update", "authc");

        // 给请求设置权限
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");

        filterMap.put("/user/*", "authc"); // 认证写在授权下面
        shiroFilterBean.setFilterChainDefinitionMap(filterMap);

        // 设置登录请求地址
        shiroFilterBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterBean.setSuccessUrl("/index");
        //未授权请求;
        shiroFilterBean.setUnauthorizedUrl("/401.html");
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
        return new UserRealm();
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     *
     * @return ShiroDialect
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}