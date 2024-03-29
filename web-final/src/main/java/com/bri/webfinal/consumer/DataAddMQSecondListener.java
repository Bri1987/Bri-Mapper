package com.bri.webfinal.consumer;

import com.bri.webfinal.enums.EventMessageType;
import com.bri.webfinal.model.EventMessage;
import com.bri.webfinal.service.FunctionService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@Component
@Slf4j
@RabbitListener(queuesToDeclare = {@Queue("data.add.second.queue")})
public class DataAddMQSecondListener
{
    @Autowired
    private FunctionService functionService;
    @RabbitHandler
    public void dataAddFirstHanlder(EventMessage eventMessage, Message message, Channel channel) throws Exception {
        log.info("监听到消息 : "+message);
        eventMessage.setEventMessageType(EventMessageType.DATA_ADD_SECOND.name());
        functionService.handleAddData(eventMessage);
        log.info("消费成功");
    }
}
