# Mybatis of SpringBoot

## 1. 导入依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>2.1.4</version>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.1.20</version>
    </dependency>
</dependencies>
```

## 2. 配置数据库连接

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    url: jdbc:mysql://localhost:3306/springboot?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
    #druid 数据源专有配置
    druid:
      initialSize: 5
      minIdle: 5
      maxActive: 20
      maxWait: 60000
      timeBetweenEvictionRunsMillis: 60000
      minEvictableIdleTimeMillis: 300000
      validationQuery: SELECT 1 FROM DUAL
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      poolPreparedStatements: true

      #配置监控统计拦截的filters，stat:监控统计、log4j：日志记录、wall：防御sql注入
      #如果允许时报错  java.lang.ClassNotFoundException: org.apache.log4j.Priority
      #则导入 log4j 依赖即可，Maven 地址：https://mvnrepository.com/artifact/log4j/log4j
      filters: stat,wall,log4j
      maxPoolPreparedStatementPerConnectionSize: 20
      useGlobalDataSourceStat: true
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
mybatis:
  type-aliases-package: io.zsy.cache.entity
  mapper-locations: classpath:/mapper/*.xml
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

## 3. 创建实体类、Mapper接口、Mapper.xml 映射文件

映射文件注意点： namespace 要与 Mapper 接口对应

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 这里对应 mapper 接口 -->
<mapper namespace="io.zsy.cache.mapper.MenuMapper">
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="io.zsy.cache.entity.Menu">
        <id column="id" property="id"/>
        <result column="parent_id" property="parentId"/>
        <result column="path" property="path"/>
        <result column="redirect" property="redirect"/>
        <result column="component" property="component"/>
        <result column="name" property="name"/>
        <result column="title" property="title"/>
        <result column="sort" property="sort"/>
        <result column="icon" property="icon"/>
        <result column="hidden" property="hidden"/>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        id, parent_id, path, redirect, component, name, title, sort, icon, hidden
    </sql>

    <select id="selectList" resultMap="BaseResultMap">
        select * from admin_menu;
    </select>
</mapper>
```

## 4. 可能遇到的问题：Invalid bound statement (not found)

此问题除了上面映射文件 namespace 等路径不匹配外，还可能由于 xml 文件在运行打包构建时被忽略，可以查看 target 中对应目录是否有 xml 文件验证。

解决方法：在 pom 文件中配置资源文件构建选项

```xml
    <build>
        <!-- 如果不添加此节点mybatis的mapper.xml文件都会被漏掉。 -->
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
```

