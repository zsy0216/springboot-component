package io.zsy.shiro.cache;

import org.apache.shiro.cache.MapCache;

import java.io.Serializable;
import java.util.Map;

/**
 * 缓存实现类, 实现序列化接口方便对象存储于第三方容器(Map存放键值对)
 * 用于存储角色、权限、用户等缓存信息
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/4 21:03
 */
public class SimpleMapCache extends MapCache<Object, Object> implements Serializable {

    public SimpleMapCache(String name, Map backingMap) {
        super(name, backingMap);
    }
}
