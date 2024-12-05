package com.spring.rabbitmq.controller;

import java.time.LocalDateTime;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rabbitmq.model.MessageDto;

@RestController
@RequestMapping("/default-exchange")
public class DefaultExchangeController {
    @Autowired
    private AmqpTemplate defaultQueue;
    @GetMapping("/message")
    public void sendMessage(){
        MessageDto messageDto = new MessageDto("default", LocalDateTime.now());
        defaultQueue.convertAndSend(messageDto);
    }
}