package io.zsy.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * 弃用，自己实现Cache ->
 * org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to java.lang.String
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/7 14:06
 */
@Deprecated
@Component
public class RedisShiroCache implements Cache<String, Object> {

    public static final String CACHE_PREFIX = "shiro:redis:cache";

    @Resource
    private RedisUtils redisUtils;

    @Override
    public Object get(String s) throws CacheException {
        return redisUtils.get(CACHE_PREFIX + s);
    }

    @Override
    public Object put(String s, Object v) throws CacheException {
        redisUtils.set(CACHE_PREFIX + s, v);
        return v;
    }

    @Override
    public Object remove(String s) throws CacheException {
        Object v = redisUtils.get(CACHE_PREFIX + s);
        redisUtils.del(s);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        redisUtils.del(keys().toArray(new String[0]));
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<String> keys() {
        return redisUtils.keys(CACHE_PREFIX);
    }

    @Override
    public Collection<Object> values() {
        return null;
    }
}
