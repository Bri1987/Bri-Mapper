package com.bri.webfinal.service.impl;

import com.bri.webfinal.service.MappingService;
import com.bri.webfinal.service.MappingTask;
import com.bri.webfinal.service.SessionService;
import com.bri.webfinal.service.WebSocketClientSyncCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MappingServiceImpl implements MappingService
{
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SessionService sessionService;

    //private WebSocketClientSyncCallback callback = null;

    @Override
    public void submit(MappingTask task, WebSocketClientSyncCallback webSocketClientSyncCallback) throws IOException, InterruptedException {
        var future=executorService.submit(task);
        //加入task
        if(sessionService.find(task.getSessionId())!=null)
            sessionService.find(task.getSessionId()).addTask(future);

        new Thread(()->{
            while (!future.isDone())
            {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                webSocketClientSyncCallback.callback();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}
