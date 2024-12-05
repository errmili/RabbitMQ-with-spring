package com.spring.rabbitmq.controller;

import java.time.LocalDateTime;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.rabbitmq.model.MessageDto;

@RestController
@RequestMapping("/fanout")
public class FanoutExchangeController {
    @Autowired
    private AmqpTemplate fanoutQueue;
    @GetMapping("/message")
    public String sendMessage() throws Exception {
        MessageDto messageDto = new MessageDto("direct", LocalDateTime.now());
        fanoutQueue.convertAndSend(messageDto);
        return "Success Fanout";
    }
}
