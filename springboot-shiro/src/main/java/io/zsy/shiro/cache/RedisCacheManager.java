package io.zsy.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 弃用，自己实现Cache ->
 * org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to java.lang.String
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/7 14:16
 */
@Deprecated
// @Component
public class RedisCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        return new RedisCache<>(cacheName);
    }
}
