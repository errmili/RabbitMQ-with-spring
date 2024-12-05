package com.spring.rabbitmq.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.rabbitmq.model.MessageDto;
import com.spring.rabbitmq.service.ConsumerService;


@RestController
@RequestMapping("/api")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;
    @GetMapping("/messages/{queueName}")
    public List<MessageDto> getMessage(@PathVariable String queueName){
        return consumerService.receiveMessages(queueName);
    }
}
