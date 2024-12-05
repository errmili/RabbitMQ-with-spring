package com.spring.rabbitmq.controller;

import java.time.LocalDateTime;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.rabbitmq.model.MessageDto;
@RestController
@RequestMapping("/topic")
public class TopicExchangeController {
    @Autowired
    private AmqpTemplate topicQueue;
    @GetMapping("/message/{key}")
    public String sendMessage(@PathVariable String key) throws Exception { // 1 2 3
        MessageDto messageDto = new MessageDto("Topic", LocalDateTime.now());
        topicQueue.convertAndSend(key, messageDto);
        return "Success Topic";
    }
}
