package com.bri.webfinal.consumer;

import com.bri.webfinal.model.EventMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;

@Component
@RabbitListener(queuesToDeclare = {@Queue("data.add.error.queue")})
public class DataAddErrorMQListener
{
    @RabbitHandler
    public void AddErrorHandler(EventMessage eventMessage, Message message, Channel channel)
    {
        System.out.println("告警:eventMessage是:"+eventMessage);
        System.out.println("告警:监听到消息ShortLinkAddMappingMQListener：message消息内容："+message);
    }
}
