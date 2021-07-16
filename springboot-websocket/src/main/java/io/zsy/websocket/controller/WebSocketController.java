package io.zsy.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import io.zsy.websocket.entity.SocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangshuaiyin
 * @date 2021-07-16 16:22
 */
@Slf4j
@ServerEndpoint("/socket")
@RestController
public class WebSocketController {
    /**
     * 记录当前在线连接数
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);
    /**
     * 存放所有在线的客户端
     */
    private static final Map<String, Session> ONLINE_CLIENTS = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        ONLINE_COUNT.incrementAndGet();
        ONLINE_CLIENTS.put(session.getId(), session);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), ONLINE_COUNT.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        ONLINE_COUNT.decrementAndGet(); // 在线数减1
        ONLINE_CLIENTS.remove(session.getId());
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), ONLINE_COUNT.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息[{}]", session.getId(), message);
        SocketMessage socketMessage = JSONObject.parseObject(message, SocketMessage.class);
        try {
            if (socketMessage != null) {
                Session toSession = ONLINE_CLIENTS.get(socketMessage.getSessionId());
                if (toSession != null) {
                    this.sendMessage(socketMessage.getMessage(), toSession);
                }
            }
        } catch (Exception e) {
            log.error("解析失败：{}", e.getMessage());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误: {}", error.getMessage());
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e.getMessage());
        }
    }
}
