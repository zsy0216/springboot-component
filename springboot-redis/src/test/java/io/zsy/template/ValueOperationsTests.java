package io.zsy.template;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangshuaiyin
 * @date 2021-08-03 15:09
 */
@SpringBootTest
public class ValueOperationsTests {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public final static String key = "KEY_BOOT_TEST_VALUE_OPERATIONS";
    public final static String value = "VALUE_BOOT_TEST_VALUE_OPERATIONS";

    @Test
    public void context() {
        // RedisOperations<K, V>
        redisTemplate.expire(key, 1L, TimeUnit.SECONDS);
        // ValueOperations<K, V>
        redisTemplate.opsForValue().get(key);
        // ListOperations<K, V>
        redisTemplate.opsForList().leftPop(key);
        // SetOperations<K, V>
        redisTemplate.opsForSet().add(key);
        // ZSetOperations<K, V>
        redisTemplate.opsForZSet().add(key, "s", 1.1);
        // HashOperations<H, HK, HV>
        redisTemplate.opsForHash().get(key, "s");
    }

    /**
     * 单个添加元素
     */
    @Test
    public void testSet() {
        // 1. 设置 key value 键值对, 重复添加相同 key 会覆盖
        redisTemplate.opsForValue().set(key, value);

        // 1.1 设置 k-v 并设置过期时间, 下面的设置均有设置过期时间方法重载，不再演示
        // redisTemplate.opsForValue().set(key, value, 100, TimeUnit.SECONDS);

        // 2. 如果 k 不存在则设置 k-v
        // redisTemplate.opsForValue().setIfAbsent(key, value + "absent1");

        // 3. 如果 k 存在则设置 k-v
        // redisTemplate.opsForValue().setIfPresent(key, value + "present");

        // 4. 用给定的value覆盖从指定offset开始的k-v值。
        // VALUE_BOOT_TEST_VALUE_OPERATIONS -> 123"E_BOOT_TEST_VALUE_OPERATIONS
        // redisTemplate.opsForValue().set(key, "123", 0);

        // 5. 设置 k 对应 v 为在 offset 位置的值？
        // \xa2VALUE_BOOT_TEST_VALUE_OPERATIONS
        // redisTemplate.opsForValue().setBit(key, 0, true);
    }

    /**
     * 单个获取元素
     */
    @Test
    public void testGet() {
        // 1. 根据 k 获取 v
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println(value);

        // 2. 获取begin和end之间的key对应value的子字符串
        String subValue = redisTemplate.opsForValue().get(key, 0, 4);
        System.out.println(subValue);

        String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, value + "new");
        System.out.println(oldValue);
    }

    /**
     * 批量添加/获取
     */
    @Test
    public void testMultiSetAndGet() {
        Map<String, Object> map = new HashMap<>();

        map.put(key+"_1", value+"_1");
        map.put(key+"_2", value+"_2");
        map.put(key+"_3", value+"_3");

        // 批量添加 k-v
        redisTemplate.opsForValue().multiSet(map);
        // k 不存在是批量添加
        redisTemplate.opsForValue().multiSetIfAbsent(map);

        // 批量获取
        List<Object> values = redisTemplate.opsForValue().multiGet(map.keySet());
        assert values != null;
        values.forEach(System.out::println);
    }

    /**
     * 对数字类型的value字符串进行加减操作
     */
    @Test
    public void testInDecrement() {
        // 将存储为key下的字符串值的整数值增加 1 。
        Long increment = redisTemplate.opsForValue().increment(key);
        System.out.println(increment);

        // 将存储为key下的字符串值的整数值增加 20 。
        redisTemplate.opsForValue().increment(key, 20);

        // 将存储为key下的字符串值的浮点数值递增delta 。
        redisTemplate.opsForValue().increment(key, 1.5);

        // 减少
        redisTemplate.opsForValue().decrement(key);
        redisTemplate.opsForValue().decrement(key, 20);
    }

    /**
     * 变更 key、获取值大小
     */
    @Test
    public void testOther() {
        // 将一个value附加到 key 对应的value后面 。
        redisTemplate.opsForValue().append(key, JSON.toJSONString("_append"));

        // 获取 value 的大小 B
        Long size = redisTemplate.opsForValue().size(key);
        System.out.println(size);
    }
}
