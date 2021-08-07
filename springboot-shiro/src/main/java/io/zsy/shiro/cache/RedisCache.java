package io.zsy.shiro.cache;

import io.zsy.shiro.util.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Set;

/**
 * 自定义 Shiro 的 Redis 缓存实现
 * 弃用，自己实现Cache ->
 * org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to java.lang.String
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/7 14:06
 */
@Deprecated
// @Component
public class RedisCache<K, V> implements Cache<K, V> {

    private String cacheName;

    public RedisCache() {
    }

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public V get(K k) throws CacheException {
        return (V) getRedisTemplate().opsForHash().get(this.cacheName, k.toString());
    }

    @Override
    public V put(K k, V v) throws CacheException {
        getRedisTemplate().opsForHash().put(this.cacheName, k.toString(), v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = get(k);
        getRedisTemplate().opsForHash().delete(this.cacheName, k.toString());
        return v;
    }

    @Override
    public void clear() throws CacheException {
        getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<K> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<V> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        // redis序列化，默认将对象序列化，更改key成String序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
