package com.bri.webfinal.service.impl;

import com.bri.webfinal.service.SessionService;
import com.bri.webfinal.session.SessionContext;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
//session存储表
public class SessionServiceImpl implements SessionService
{
    private final Map<String, SessionContext> sessions=new ConcurrentHashMap<>();

    //加入新session
    public void new_session(WebSocketSession session)
    {
        sessions.put(session.getId(),new SessionContext(session));
    }

    //通过key找session
    public SessionContext find(String sessionId)
    {
        return sessions.get(sessionId);
    }

    //根据sessionId来send message
    public void notify_message(String sessionId,String file_id) throws IOException {
        if(sessions.get(sessionId)!=null)
        {
            sessions.get(sessionId).notify_message(file_id);
        }
    }

    //移除session
    public void destroy(String sessionId)
    {
        if(sessions.get(sessionId)!=null)
        {
            //TODO 如果直接execute不用submit，有rabbitMq处理异常，还需要cancelTask吗，session断网直接迅速失败五次，就直接结束?
            //这个队列的好处在于每个sessionId都有一个队列，而rabbitMq不是这样，但性能是ok的
            sessions.get(sessionId).cancelTask();
            sessions.remove(sessionId);
        }
    }
}
