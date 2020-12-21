# SpringBoot Component
    
使用SpringBoot整合其他相关技术，搭建脚手架工程便于项目使用。


# Spring Boot 优势
- 遵循“约定优于配置”的原则，简化配置；
- 可以完全脱离 XML 配置文件，采用注解配置和 java Config；
- 内嵌 Servlet 容器，应用可用 jar 包执行：java -jar
- 快速完成项目搭建、整合第三方类库，方便易用；
- 提供了 starter POM，能够非常方便的进行包管理，简化包管理配置；
- 与 Spring Cloud 天然融合，spring boot 是目前 java 体系内实现微服务最佳方案；

# Spring Boot 2.x 相对 1.x 重要的变化
- 最低的 JDK 支持版本 1.8
- 内置嵌入式 tomcat 8.5
- Thymeleaf3.0 代替 2.0
- 默认数据库连接池已从 Tomcat 切换到 HikariCP
- redis 客户端默认使用 Lettuce 替换 Jedis
- 响应式编程 WebFlux
