package io.zsy.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * Application Event 为Bean与Bean之间的消息通信提供了支持
 *
 * @author zhangshuaiyin
 * @date 2021-06-30 14:44
 */
public class LogEvent extends ApplicationEvent {
    public LogEvent(Object source) {
        super(source);
    }
}
