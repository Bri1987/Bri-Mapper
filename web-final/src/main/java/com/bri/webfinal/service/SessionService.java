package com.bri.webfinal.service;

import com.bri.webfinal.session.SessionContext;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface SessionService
{
    //加入新session
    public void new_session(WebSocketSession session);

    //通过key找session
    public SessionContext find(String sessionId);

    //移除session
    public void destroy(String sessionId);

    //根据sessionId来send message
    public void notify_message(String sessionId,String map_source_id) throws IOException;
}
