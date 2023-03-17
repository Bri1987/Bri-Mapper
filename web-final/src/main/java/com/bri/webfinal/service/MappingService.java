package com.bri.webfinal.service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public interface MappingService
{
    void submit(MappingTask task,WebSocketClientSyncCallback webSocketClientSyncCallback) throws IOException, InterruptedException;
}
