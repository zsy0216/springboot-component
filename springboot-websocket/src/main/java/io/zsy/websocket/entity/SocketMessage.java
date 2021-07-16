package io.zsy.websocket.entity;

import lombok.Data;

/**
 * @author zhangshuaiyin
 * @date 2021-07-16 16:43
 */
@Data
public class SocketMessage {
    private String sessionId;
    private String message;
}
