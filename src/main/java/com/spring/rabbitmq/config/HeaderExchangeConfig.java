package com.spring.rabbitmq.config;

import javax.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HeaderExchangeConfig {
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Value("${rabbit.header1.queue}")
    private String headerQueue1;
    @Value("${rabbit.header2.queue}")
    private String headerQueue2;
    @Value("${rabbit.header3.queue}")
    private String headerQueue3;
    @Value("${rabbit.header.exchange}")
    private String exchange;
    @Bean
    Queue createHeaderQueue1(){
        return new Queue(headerQueue1,true,false,false);
    }
    @Bean
    Queue createHeaderQueue2(){
        return new Queue(headerQueue2,true,false,false);
    }
    @Bean
    Queue createHeaderQueue3(){
        return new Queue(headerQueue3,true,false,false);
    }
    @Bean
    Binding createHeaderBinding1(){// ||
        return BindingBuilder.bind(createHeaderQueue1()).to(createHeaderExchange()).whereAny("error","warning").exist();
    }
    @Bean
    Binding createHeaderBinding2(){
        return BindingBuilder.bind(createHeaderQueue2()).to(createHeaderExchange()).whereAny("warning","debug").exist();
    }
    @Bean
    Binding createHeaderBinding3(){
        return BindingBuilder.bind(createHeaderQueue3()).to(createHeaderExchange()).whereAll("error","warning","debug").exist();
    }
    @Bean
    HeadersExchange createHeaderExchange(){
        return new HeadersExchange(exchange,true,false);
    }
    @Bean
    public AmqpTemplate headerQueue(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(exchange);
        return rabbitTemplate;
    }
    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createHeaderQueue1());
        amqpAdmin.declareQueue(createHeaderQueue2());
        amqpAdmin.declareQueue(createHeaderQueue3());
        amqpAdmin.declareExchange(createHeaderExchange());
        amqpAdmin.declareBinding(createHeaderBinding1());
        amqpAdmin.declareBinding(createHeaderBinding2());
        amqpAdmin.declareBinding(createHeaderBinding3());
    }
}