package com.bri.webfinal.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RabbitMQErrorConfig
{
    private String dataAddErrorExchange="data.add.error.exchange";
    private String dataAddErrorQueue="data.add.error.queue";
    private String dataAddRoutingKey="data.add.error.routing.key";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //异常交换机
    @Bean
    public TopicExchange errorTopicExchange()
    {
        return new TopicExchange(dataAddErrorExchange,true,false);
    }

    //异常队列
    @Bean
    public Queue errorQueue()
    {
        return new Queue(dataAddErrorQueue,true);
    }

    //routing-key(binding-key)与队列与交换机绑定
    @Bean
    public Binding BindingErrorQueueAndExchange(Queue errorQueue,TopicExchange errorTopicExchange)
    {
        return BindingBuilder.bind(errorQueue).to(errorTopicExchange).with(dataAddRoutingKey);
    }
}
