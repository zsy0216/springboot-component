package io.zsy.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import io.zsy.shiro.cache.RedisShiroCacheManager;
import io.zsy.shiro.filter.AnyRolesAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置类
 *
 * @author: zhangshuaiyin
 * @date: 2021/5/30 9:51
 */
@Slf4j
@Configuration
public class ShiroConfig {
    /**
     * Shiro 过滤器
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        log.info("shiro request filter");
        ShiroFilterFactoryBean shiroFilterBean = new ShiroFilterFactoryBean();
        // 关联 DefaultWebSecurityManager
        shiroFilterBean.setSecurityManager(securityManager());

        // 添加自定义的过滤器
        Map<String, Filter> map = new HashMap<>();
        map.put("any-roles", new AnyRolesAuthorizationFilter());
        shiroFilterBean.setFilters(map);

        // 添加 shiro 的内置过滤器
        /*
         anon: 无需认证即可访问
         authc: 必须认证之后才能访问
         user: 必须拥有记住我功能才能访问
         perms: 拥有某个资源权限才能访问
         roles: 拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        // 给请求设置权限/角色
        // 角色设置要在权限之前
        // 使用自定义角色过滤器
        filterMap.put("/user/**", "any-roles[admin, guest]");

        // 默认的角色过滤器
        // filterMap.put("/user/**", "roles[admin, guest]");

        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");

        // 认证请求写在授权下面
        filterMap.put("/**", "authc");
        shiroFilterBean.setFilterChainDefinitionMap(filterMap);

        // 设置登录请求地址
        shiroFilterBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterBean.setSuccessUrl("/index");
        //未授权请求;
        shiroFilterBean.setUnauthorizedUrl("/401.html");
        return shiroFilterBean;
    }

    /**
     * securityManager 权限管理器
     *
     * @return securityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        log.info("security manager");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 管理 realm
        securityManager.setRealm(userRealm());
        // 管理 session 会话
        securityManager.setSessionManager(sessionManager());
        // 缓存管理器
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }

    /**
     * Realm 身份认证授权
     *
     * @return Realm
     */
    @Bean
    public Realm userRealm() {
        log.info("get user realm");
        UserRealm realm = new UserRealm();
        // realm.setCacheManager(cacheManager());
        return realm;
    }

    /**
     * ShiroDialect：配置后可以在thymeleaf里使用shiro的标签进行鉴权
     *
     * @return ShiroDialect
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 缓存管理器，可配置EhCache 或 Redis 或内存
     *
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        log.info("配置缓存管理器");
        // return ehCacheManager();
        return redisCacheManager();
        // return new MemoryConstrainedCacheManager();
        // return new RedisShiroCacheManager();
    }

    /**
     * Redis 管理
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1");
        redisManager.setPort(6379);
        redisManager.setPassword("123456");
        return redisManager;
    }

    /**
     * Redis 缓存管理器
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("username");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    /**
     * EhCache 缓存管理器
     *
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        log.info("ehcache 缓存管理器");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    /**
     * 创建 Cookie 对象
     *
     * @return
     */
    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("springboot-shiro-session");
        return simpleCookie;
    }

    /**
     * 会话管理器
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 关闭会话更新
        sessionManager.setSessionValidationSchedulerEnabled(false);
        // 设置 cookie 生效
        sessionManager.setSessionIdCookieEnabled(true);
        // 指定 cookie 生成策略
        sessionManager.setSessionIdCookie(simpleCookie());
        // 全局 session 超时时间 1h
        sessionManager.setGlobalSessionTimeout(3600000);
        return sessionManager;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     *
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * AOP式方法级权限检查
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持. 配合DefaultAdvisorAutoProxyCreator事项注解权限校验
     *
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
}
