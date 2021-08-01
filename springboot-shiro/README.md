# SpringBoot + Shiro + Web

优化参考：

1. https://www.yuque.com/jiaqi.ding/reading/nt0gbc
2. https://www.yuque.com/tangwx/wlxqwc/ruylah

## 前置准备

1. 导入依赖

```xml
<!-- https://mvnrepository.com/artifact/org.apache.shiro/shiro-spring-boot-web-starter -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-web-starter</artifactId>
    <version>1.7.1</version>
</dependency>
```

2. 添加配置 ShiroConfig

`src/main/java/io/zsy/shiro/config/ShiroConfig.java`

## 准备页面

首页：index.html

登录页：login.html

用户添加页面：user/add.html

用户修改页面：user/update.html

## 拦截未登录请求

**shiro 的内置过滤器**

```
anon: 无需认证即可访问
authc: 必须认证之后才能访问
user: 必须拥有记住我功能才能访问
perms: 拥有某个资源权限才能访问
roles: 拥有某个角色权限才能访问
```

如果默认什么都不配置，那么不登录就可以访问添加和修改页面，通过以下配置就可以完成对未登录请求的拦截，实现登录后再访问。

`ShiroConfig # shiroFilterFactoryBean`

## 登录认证

1. 用户登录

`src/main/java/io/zsy/shiro/controller/HelloController.java`

2. shiro 认证

`src/main/java/io/zsy/shiro/config/UserRealm.java`

此时，登录后就可以请求之前的 用户添加和修改页面了。

## 授权示例

`ShiroConfig # shiroFilterFactoryBean`

```java
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
        filterMap.put("/user/**", "roles[admin]");

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
```

访问 /user/add 需要拥有 user:add 权限

`src/main/java/io/zsy/shiro/config/UserRealm.java`

```java
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
```

这里默认给所有需要授权的用户添加了该权限，实际开发中应该从数据库中读取登录用户的权限，判断是否授权成功；

这里需要在认证的时候，将当前登录的用户传入 AuthenticationInfo 对象中，然后通过该对象去除登录的用户判断是否有权限；

## 整合 Thymeleaf

导入依赖
```xml
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>2.0.0</version>
</dependency>
```

添加配置 `ShiroConfig`

```java
@Bean
public ShiroDialect shiroDialect(){
    return new ShiroDialect();
}
```

参考 index.html

