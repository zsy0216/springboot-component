package io.zsy.common.event;

import io.zsy.common.event.dto.LogEventDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author zhangshuaiyin
 * @date 2021-06-30 14:44
 */
@Component
public class EventTrigger {
    private final ApplicationEventPublisher publisher;

    public EventTrigger(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publisher(Object data){
        if (data instanceof LogEventDTO) {
            publisher.publishEvent(new LogEvent(data));
        }
    }
}
