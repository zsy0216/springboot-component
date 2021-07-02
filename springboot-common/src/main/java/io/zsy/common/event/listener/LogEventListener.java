package io.zsy.common.event.listener;

import io.zsy.common.event.LogEvent;
import io.zsy.common.event.dto.LogEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 事件监听器
 * implements ApplicationListener<LogEvent>：可指定监听的事件
 *
 * @author zhangshuaiyin
 * @date 2021-06-30 14:49
 */
@Slf4j
@Component
public class LogEventListener {

    @EventListener
    @Async
    public void saveLogEvent(LogEvent logEvent) {
        LogEventDTO logEventDTO = (LogEventDTO) logEvent.getSource();

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        log.info(requestAttributes.getSessionId());

        log.warn("这里可以持久化操作日志");
        log.info("Event 数据- userId: {}, log: {}, createDate: {}", logEventDTO.getUserId(), logEventDTO.getLogRemark(), logEventDTO.getCreateTime());

    }
}
