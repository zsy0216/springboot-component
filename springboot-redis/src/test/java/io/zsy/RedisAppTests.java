package io.zsy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.zsy.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author: zhangshuaiyin
 * @date: 2021/6/26 12:57
 */
@SpringBootTest
public class RedisAppTests {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Resource
    RedisUtils redisUtils;

    public String key = "GlobalChannelInfo_Id_v2:85796cdbadf346beb207cdd9eba57ef9";

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
        // redisTemplate.opsForValue().set("mykey", "zsy");
        System.out.println(redisTemplate.opsForValue().get("GlobalChannelGroupInfo_Id:8120807f37f84b7382a5ad2c0b2a33a0"));
    }

    @Test
    void testRedisUtils() {
        System.out.println(JSONObject.toJSONString(redisUtils.get(key)));
    }
}
