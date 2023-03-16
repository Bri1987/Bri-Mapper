package com.bri.webfinal.session;

import com.bri.webfinal.message.Message;
import com.bri.webfinal.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
//WebSocket的消息处理类
public class SessionHandler extends TextWebSocketHandler {
    @Autowired
    private SessionService sessionService;

    private final ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessionService.new_session(session);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(new Message("sessionId",session.getId()))));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
    {
        sessionService.destroy(session.getId());
    }
}
