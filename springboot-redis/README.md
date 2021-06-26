# Springboot 整合 redis

SpringBoot 2.x 之后默认客户端由 Jedis 变为 lettuce

- jedis: 采用直连, 多个线程操作的话, 是不安全的, 如果想要避免不安全, 使用 jedis pool 连接池 它更像BIO

- lettuce: 采用netty 实例可以多个线程中进行共享, 不存在线程不安全的情况, 可以减少线程数据 它更像NIO

## 导入依赖+配置+简单测试

1. pom.xml

   ```xml
   <!-- 默认使用lettuce客户端 -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
   ```

   

2. application.yaml

   ```yaml
   # SpringBoot 所有的配置类, 都有一个自动配置类 RedisAutoConfiguration
   # 自动配置类都会绑定一个 properties 配置文件 RedisProperties
   spring:
     redis:
       host: 127.0.0.1
       password: 123456
       port: 6379
   ```

3. 测试

   ```java
   @SpringBootTest
   public class RedisAppTests {
       @Autowired
       RedisTemplate<String, String> redisTemplate;
       @Test
       void contextLoads() {
           redisTemplate.opsForValue().set("mykey", "zsy");
           System.out.println(redisTemplate.opsForValue().get("mykey")); // zsy
       }
   }
   
   ```

   

