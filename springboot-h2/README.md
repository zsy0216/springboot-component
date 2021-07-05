# SpringBoot 整合 h2 内嵌数据库

H2是一个短小精干的嵌入式数据库引擎，主要的特性包括：

- 免费、开源、快速

- 嵌入式的数据库服务器，支持集群

- 提供JDBC、ODBC访问接口，提供基于浏览器的控制台管理程序

- Java编写，可使用GCJ和IKVM.NET编译

- 短小精干的软件，1M左右。

在这里，使用 h2 和 MybatisPlus 为例，整合内嵌式数据库，避免使用数据库必须连接客户端的弊端。

## 1. 引入依赖

```xml
 <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.2</version>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

## 2. 配置文件

```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    schema: classpath:db/schema.sql # 每次启动程序，程序都会运行schema.sql文件，对数据库的数据操作
    data: classpath:db/data.sql # 每次启动程序，程序都会运行data.sql文件，对数据库的数据操作
    url: jdbc:h2:mem:test # 配置h2数据库的连接地址
    username: root
    password: root
  h2:
    console:
      enabled: true #开启web console功能

```

## 3. 编写实体和映射 Mapper

User.java

```java
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Model<User> {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}

```

UserMapper.java

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
```

## 4. 测试

```java
@SpringBootTest
public class SampleTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(userList.size() == 5, "验证未通过");
        userList.forEach(System.out::println);
    }
}
```

## 5. Web 控制台

启动项目，访问 http://localhost:8080/h2-console/

配置数据库信息，即可连接到控制台操作数据。
