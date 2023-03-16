package com.bri.webfinal.service.impl;

import com.bri.webfinal.service.MappingService;
import com.bri.webfinal.service.MappingTask;
import com.bri.webfinal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class MappingServiceImpl implements MappingService
{
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private SessionService sessionService;

    @Override
    public void submit(MappingTask task) {
        var future=executorService.submit(task);
        //加入task
        if(sessionService.find(task.getSessionId())!=null)
            sessionService.find(task.getSessionId()).addTask(future);

    }
}
