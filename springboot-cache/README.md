# Springboot Cache of SpringBoot

本工程以 springboot-mybatis 为模板构建

## 1. 导入依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

## 2. 配置测试数据数据库连接

```yaml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=false
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
mybatis:
  type-aliases-package: io.zsy.cache.entity
  mapper-locations: classpath:/mapper/*.xml
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

## 3. 主启动类增加 @EnableCaching 注解

```java
@EnableCaching
@SpringBootApplication
public class CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }
}
```

## 4. 在 Mapper 接口和方法上增加缓存注解
```java
@CacheConfig(cacheNames = "menus")
@Mapper
public interface MenuMapper {

    /**
     * 查询菜单列表
     *
     * @return List
     */
    @Cacheable
    List<Menu> selectList();
}
```
这样在多次调用 selectList 查询数据库时，只会第一次查询数据库，其他都会读取缓存数据，减少数据库连接次数，提高效率

