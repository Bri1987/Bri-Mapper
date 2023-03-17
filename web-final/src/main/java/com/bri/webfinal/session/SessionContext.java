package com.bri.webfinal.session;

import com.bri.webfinal.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionContext
{
    private WebSocketSession session;

    private final ObjectMapper objectMapper=new ObjectMapper();

    private List<Future<?>> tasks=new ArrayList<>();

    public SessionContext(WebSocketSession session) {
        this.session = session;
    }

    //发送消息
    //转换成功后："file_id: 具体file名字"
    public void notify_message(String map_source_id) throws IOException {
        //TextMessage后是要发送的具体内容
        synchronized (session){
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new Message("file_id",map_source_id))));
        }
    }

    //增加task
    public void addTask(Future<?> future)
    {
        tasks.add(future);
    }

    //取消task
    public void cancelTask()
    {
        for(var task:tasks)
        {
            if(!task.isDone() && !task.isCancelled())
            {
                //task.cancel的话是整个MappingTask被取消掉，不是只取消该future
                task.cancel(true);
                //tasks.remove(task);          //TODO mine
            }
        }
    }
}
