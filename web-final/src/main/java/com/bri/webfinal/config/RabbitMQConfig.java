package com.bri.webfinal.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RabbitMQConfig
{
    //交换机
    private String dataEventExchange="data.first.second.exchange";
    @Bean
    public Exchange dataEventExchange()
    {
        return new TopicExchange(dataEventExchange,true,false);
    }

    //增量队列
    private String dataAddFirstQueue="data.add.first.queue";
    private String dataAddSeconfQueue="data.add.second.queue";

    //发送消息使用的routing-key
    private String dataAddRoutingKey="data.add.first.second.routing.key";

    //绑定队列与交换机的binding-key
    private String dataAddFirstBindingKey="data.add.first.*.routing.key";
    private String dataAddSecondBindingKey="data.add.*.second.routing.key";

    //binding-key与队列与交换机关系绑定
    @Bean
    public Binding dataFirstBinding()
    {
        return new Binding(dataAddFirstQueue,Binding.DestinationType.QUEUE,dataEventExchange,dataAddFirstBindingKey,null);
    }
    @Bean
    public Binding dataSecondBinding()
    {
        return new Binding(dataAddSeconfQueue,Binding.DestinationType.QUEUE,dataEventExchange,dataAddSecondBindingKey,null);
    }

    //普通队列，用于被监听
    @Bean
    public Queue dataAddFirstQueue()
    {
        return new Queue(dataAddFirstQueue,true,false,false);
    }
    @Bean
    public Queue dataAddSecondQueue()
    {
        return new Queue(dataAddSeconfQueue,true,false,false);
    }
}
