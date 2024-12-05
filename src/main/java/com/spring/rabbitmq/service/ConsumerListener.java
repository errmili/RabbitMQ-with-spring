package com.spring.rabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.spring.rabbitmq.model.MessageDto;


@Component
public class ConsumerListener {

    private static final String HEADER_X_RETRY = "x-retry";
    private static final int MAX_RETRY = 3;
    @Autowired
    private AmqpTemplate directQueue;


    @RabbitListener(queues = {"${rabbit.direct1.queue}","${rabbit.direct3.queue}"},
            containerFactory = "simpleRabbitListenerContainerFactory")
    public void receiveMessages(@Payload MessageDto messageDto, Message message){
        try {
            System.out.println(messageDto.getStatus() + "  " + messageDto.getLocalDateTime());
            throw new RuntimeException();
        } catch (Exception e) {
            Integer count = (Integer) message.getMessageProperties().getHeaders().get(HEADER_X_RETRY);
            if (count == null) {
                count = 0;
            } else if(count >= MAX_RETRY){
                System.out.println("-----> message ignored");
                return;
            }
            System.out.println("Retry...........");
            message.getMessageProperties().getHeaders().put(HEADER_X_RETRY,++count);
            directQueue.send(message.getMessageProperties().getReceivedRoutingKey(),message);
        }
    }
}