package com.bri.webfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
//支持异步任务的线程池并发工具
public class ExecutorConfig {
    @Bean
    public ExecutorService getExecutor()
    {
        return Executors.newFixedThreadPool(8);
    }
}
