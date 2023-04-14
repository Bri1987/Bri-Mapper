package com.bri.webfinal.session;

import com.bri.webfinal.message.Message;
import com.bri.webfinal.service.SessionService;
import com.bri.webfinal.service.WebSocketClientSyncCallback;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
//WebSocket的消息处理类
public class SessionHandler extends TextWebSocketHandler {
    @Autowired
    private SessionService sessionService;

    private final ObjectMapper objectMapper=new ObjectMapper();

    private WebSocketClientSyncCallback callback = null;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessionService.new_session(session);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(new Message("sessionId",session.getId()))));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)  {
        System.out.println("关闭");
        System.out.println(session.getId());
        sessionService.destroy(session.getId());
    }


//    @Override
//    public void handleTextMessage(WebSocketSession session,TextMessage message) throws IOException {
//        System.out.println("lll");
//        if(callback!=null)
//            callback.callback();
//    }
}
