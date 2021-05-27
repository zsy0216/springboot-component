package io.zsy.common.listener;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存日志监听器
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:03
 */
public class EventLoggerListener implements CacheEventListener<Object, Object> {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerListener.class);

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        logger.info("缓存事件: 类型[{}], 键值[{}], 原值[{}], 新值[{}]",
                event.getType(),
                event.getKey(),
                event.getOldValue(),
                event.getNewValue());
    }
}
