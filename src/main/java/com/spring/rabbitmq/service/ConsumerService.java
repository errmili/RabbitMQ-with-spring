package com.spring.rabbitmq.service;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.rabbitmq.model.MessageDto;


@Service
public class ConsumerService {
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private int getCountMessage(String queueName){
        Properties property = amqpAdmin.getQueueProperties(queueName);
        return (int) property.get(RabbitAdmin.QUEUE_MESSAGE_COUNT);
    }
    public List<MessageDto> receiveMessages(String queueName){
        int count = getCountMessage(queueName);
        return IntStream.range(0,count)
                .mapToObj(value -> (MessageDto)rabbitTemplate.receiveAndConvert(queueName))
                .collect(Collectors.toList());
    }
}