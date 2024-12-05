package com.spring.rabbitmq.config;

import javax.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.AmqpTemplate;

@Configuration
public class DirectExchangeConfig {
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Value("${rabbit.direct1.queue}")
    private String directQueue1;
    @Value("${rabbit.direct2.queue}")
    private String directQueue2;
    @Value("${rabbit.direct3.queue}")
    private String directQueue3;
    @Value("${rabbit.direct1.bi}")
    private String binding1;
    @Value("${rabbit.direct2.bi}")
    private String binding2;
    @Value("${rabbit.direct3.bi}")
    private String binding3;
    @Value("${rabbit.direct.exchange}")
    private String exchange;

    @Value("${rabbit.deed-line-queue}")
    private String directQueue4;

    @Bean
    Queue createDirectQueue1(){

        return QueueBuilder.durable(directQueue1)
                .deadLetterExchange("")// default
                .deadLetterRoutingKey(directQueue4)
                .build();
        //return new Queue(directQueue1,true,false,false);
    }

    @Bean
    Queue createDeedLineQueue(){
        return new Queue(directQueue4,true,false,false);
    }
    @Bean
    Queue createDirectQueue2(){
        return new Queue(directQueue2,true,false,false);
    }
    @Bean
    Queue createDirectQueue3(){
        return new Queue(directQueue3,true,false,false);
    }
    @Bean
    DirectExchange createDirectExchange(){
        return new DirectExchange(exchange,true,false);
    }
    @Bean
    Binding createDirectBinding1(){
        return BindingBuilder.bind(createDirectQueue1()).to(createDirectExchange()).with(binding1);
    }
    @Bean
    Binding createDirectBinding2(){
        return BindingBuilder.bind(createDirectQueue2()).to(createDirectExchange()).with(binding2);
    }
    @Bean
    Binding createDirectBinding3(){
        return BindingBuilder.bind(createDirectQueue3()).to(createDirectExchange()).with(binding3);
    }

    @Bean
    public AmqpTemplate directQueue(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(exchange);
        return rabbitTemplate;
    }

    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createDirectQueue1());
        amqpAdmin.declareQueue(createDirectQueue2());
        amqpAdmin.declareQueue(createDirectQueue3());
        amqpAdmin.declareQueue(createDeedLineQueue());

        amqpAdmin.declareExchange(createDirectExchange());
        amqpAdmin.declareBinding(createDirectBinding1());
        amqpAdmin.declareBinding(createDirectBinding2());
        amqpAdmin.declareBinding(createDirectBinding3());
    }
}
