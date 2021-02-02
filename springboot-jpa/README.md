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

- create: 每次加载 hibernate 都会删除上一次生成的表，然后根据实体重新生成新表，会导致数据库表数据丢失；
- create-drop: 每次加载 hibernate 时会根据实体生成表，但是 sessionFactory 一关闭，即程序退出时，表会自动删除；
-  none: 不自动创建、更新、验证数据库表结构
- **update**: 每次加载 hibernate  会根据实体更新数据库表结构（没有表则新增），对数据无影响；
- validate: 每次加载 hibernate  时验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值；

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(updatable = false, nullable = false)
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(nullable = false)
    private Date updateTime;
}
```
这里使用了 lombok 和自动生成时间

常用注解：

- @Entity: 声明实体类是 JPA 实体，告诉 JPA 在程序运行时根据实体类生成对应的表；

- @Table: 声明表信息，表名等，若表名和实体类同名，可省略；
- @ID: 声明该变量为主键；
- @GeneratedValue: 设置主键生成策略，此方式依赖于具体的数据库
- @Column: 声明属性对应表字段信息
- @Temporal: 当我们使用到 java.util 包中的时间日期类型，则需要此注接来说明转化成java.util包中的类型。
  - TemporalType.DATE（2008-08-08）
  - TemporalType.TIME（20:00:00）
  - TemporalType.TIMESTAMP（2008-08-08 20:00:00.000000001）
- @Enumerated: 映射枚举字段，以 String 类型存入数据库；注入数据库的类型有两种：EnumType.ORDINAL（Interger）、EnumType.STRING（String）；
- @MappedSuperclass: 实现将实体类的多个属性分别封装到不同的非实体类中，注解的类将不是完整的实体类，不会映射到数据库表，但其属性将映射到子类的数据库字段，注解的类不能再标注@Entity或@Table注解，也无需实现序列化接口，注解的类继承另一个实体类 或 标注@MappedSuperclass类，他可使用@AttributeOverride 或 @AttributeOverrides注解重定义其父类属性映射到数据库表中字段。
- @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")：将Date属性转换为String类型， timezone解决（相差8小时）

---

- @CreatedDate:  表示字段为创建时间字段（insert自动设置）
- @CreatedBy: 创建用户字段（insert自动设置）
- @LastModifiedDate: 最后修改时间字段（update自定设置）
- @LastModifiedBy: 最后修改用户字段（update自定设置）

用法：

1. @EntityListeners(AuditingEntityListener.class)：申明实体类并加注解
2. @EnableJpaAuditing：在启动类中加此注解
3. 在实体类中对应的属性上加上面四种注解
4. 自定义添加用户

## 4. 创建操作数据库的接口
```java
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
```

该接口需继承自 CrudRepository<T, ID> 接口，默认自带了基本的 CRUD 操作。
