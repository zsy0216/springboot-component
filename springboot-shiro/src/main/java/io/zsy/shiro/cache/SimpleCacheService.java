package io.zsy.shiro.cache;

import io.zsy.shiro.util.SerializableUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhangshuaiyin
 * @date: 2021/8/4 21:22
 */
@Component
public class SimpleCacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void createCache(String name, Cache<Object, Object> cache) {
        stringRedisTemplate.opsForValue().set(name,
                Objects.requireNonNull(SerializableUtils.serialize(cache)),
                SecurityUtils.getSubject().getSession().getTimeout() / 1000,
                TimeUnit.SECONDS);
    }

    public Cache<Object, Object> getCache(String name) throws CacheException {
       return (Cache<Object, Object>) SerializableUtils.deserialize(stringRedisTemplate.opsForValue().get(name));
    }

    public void removeCache(String name) throws CacheException {
        stringRedisTemplate.delete(name);
    }

    public void updateCache(String name, Cache<Object, Object> cache){
        createCache(name, cache);
    }

}
