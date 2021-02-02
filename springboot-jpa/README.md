# Spring Data JPA

## 1. 引入依赖
```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

## 2. 新增配置
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql:///component_jpa
    username: root
    password: root
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create
```

jpa.hibernate.ddl-auto 取值说明：

- create: 
- create-drop: 
- none: 
- update: 
- validate: 

## 3. 创建实体

```java
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "jpa_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25)
    private String name;

    @Column(length = 99)
    private String email;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
}
```
这里使用了 lombok 和自动生成时间

常用注解：

- @Table: 声明表信息
- @ID: 主键 id
- @GeneratedValue: 主键生成规则
- @Column: 表字段信息

## 4. 创建操作数据库的接口
```java
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
```

该接口需继承自 CrudRepository<T, ID> 接口，默认自带了基本的 CRUD 操作。
