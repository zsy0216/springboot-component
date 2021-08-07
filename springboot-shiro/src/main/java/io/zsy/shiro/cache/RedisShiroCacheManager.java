package io.zsy.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 弃用，自己实现Cache ->
 * org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to java.lang.String
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/7 14:16
 */
@Deprecated
@Component
public class RedisShiroCacheManager implements CacheManager {

    @Resource
    private RedisShiroCache redisShiroCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return (Cache<K, V>) redisShiroCache;
    }
}
