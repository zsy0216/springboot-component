# SpringBoot + Shiro + Web

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

```java

@Slf4j
@Configuration
public class ShiroConfig {
    // ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 关联 DefaultWebSecurityManager
        bean.setSecurityManager(securityManager);
        return bean;
    }

    // DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") Realm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联 realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 创建 Realm 对象, 需要实现Realm类型的类(可以写在配置类外部抽离)
    @Bean
    public Realm userRealm() {
        return new AuthorizingRealm() {
            // 授权
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
                log.info("执行授权~~~");
                return null;
            }

            // 认证
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
                log.info("执行认证~~~");
                return null;
            }
        };
    }
}
```

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
role: 拥有某个角色权限才能访问
```

如果默认什么都不配置，那么不登录就可以访问添加和修改页面，通过以下配置就可以完成对未登录请求的拦截，实现登录后再访问。

`ShiroConfig # shiroFilterFactoryBean`

```java
@Bean
public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterBean=new ShiroFilterFactoryBean();
        // 关联 DefaultWebSecurityManager
        shiroFilterBean.setSecurityManager(securityManager);

        // 添加 shiro 的内置过滤器
        Map<String, String> filterMap=new LinkedHashMap<>();

        // 支持通配符 filterMap.put("/user/*", "authc"); user/下的请求必须认证
        filterMap.put("/user/add","authc");
        filterMap.put("/user/update","authc");
        shiroFilterBean.setFilterChainDefinitionMap(filterMap);

        // 设置登录请求地址
        shiroFilterBean.setLoginUrl("/login");
        return shiroFilterBean;
        }
```

## 登录认证

1. 用户登录

```java
@PostMapping("/login")
public String login(String username,String password,Model model){
        // 获取当前用户
        Subject subject=SecurityUtils.getSubject();
        // 封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        try{
        // 执行登录操作
        subject.login(token);
        return"index";
        }catch(UnknownAccountException e){
        model.addAttribute("msg","用户名错误");
        return"login";
        }catch(IncorrectCredentialsException e){
        model.addAttribute("msg","密码错误");
        return"login";
        }
        }
```

2. shiro 认证

`ShiroConfig # userRealm`

```java
@Bean
public Realm userRealm(){
        return new AuthorizingRealm(){
// 认证
@Override
protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)throws AuthenticationException{
        log.info("执行认证~~~");
        // 伪造用户数据
        String username="root";
        String password="123456";
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        if(!username.equals(token.getUsername())){
        return null; // 抛出异常 UnknownAccountException
        }
        // shiro 进行密码认证
        return new SimpleAuthenticationInfo("",password,"");
        }
        }
        }
```

此时，登录后就可以请求之前的 用户添加和修改页面了。

## 授权示例

`ShiroConfig # shiroFilterFactoryBean`

```java
@Bean
public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        // ...
        Map<String, String> filterMap=new LinkedHashMap<>();

        // 给请求设置权限 访问 /user/add 需要拥有 user:add 权限
        filterMap.put("/user/add","perms[user:add]");

        shiroFilterBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterBean;
        }
```

访问 /user/add 需要拥有 user:add 权限

`ShiroConfig # userRealm`

```java
 // 授权
@Override
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){
        log.info("执行授权~~~");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");
        return info;
        }
```

这里默认给所有需要授权的用户添加了该权限，实际开发中应该从数据库中读取登录用户的权限，判断是否授权成功；

这里需要在认证的时候，将当前登录的用户传入 AuthenticationInfo 对象中，然后通过该对象去除登录的用户判断是否有权限；

```java
// 认证对象
return new SimpleAuthenticationInfo(user,password,"");

// 授权时取出
        Subject subject=SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
// 设置权限
        info.serStringPermissions(user.getPerms());

```

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


