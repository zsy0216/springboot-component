package io.zsy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: zhangshuaiyin
 * @date: 2021/6/26 12:57
 */
@SpringBootTest
public class RedisAppTests {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Test
    void contextLoads() {
        // redisTemplate
        // opsForValue() 操作字符串 类似 String
        // opsForList() 操作List 类似 List
        // opsForSet()
        // opsForHash()
        // opsForZSet()
        // opsForGeo()
        // opsForHyperLogLog()

        // 除了基本操作, 常用的方法都可以直接 redisTemplate 操作 例如事务的CRUD

        // 获取 redis 连接对象
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        connection.flushAll();

        System.out.println("RedisConnectionFactory: " + redisTemplate.getConnectionFactory());
        redisTemplate.opsForValue().set("mykey", "zsy");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }
}
