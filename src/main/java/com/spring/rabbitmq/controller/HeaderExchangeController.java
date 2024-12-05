package com.spring.rabbitmq.controller;

import java.time.LocalDateTime;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.rabbitmq.model.MessageDto;

@RestController
@RequestMapping("/header")
public class HeaderExchangeController {
    @Autowired
    private AmqpTemplate headerQueue;
    @GetMapping("/message") // local8080/header?debug=sdf&error=dfs
    public String sendMessage(
            @RequestParam(value = "error",required = false) String error,
            @RequestParam(value = "warning",required = false) String warning,
            @RequestParam(value = "debug",required = false) String debug
    ){
        MessageDto messageDto = new MessageDto("Header", LocalDateTime.now());
        MessageBuilder messageBuilder = MessageBuilder.withBody(messageDto.toString().getBytes());
        if(error != null){
            messageBuilder.setHeader("error",error);
        }
        if(warning != null){
            messageBuilder.setHeader("warning",warning);
        }
        if(debug != null){
            messageBuilder.setHeader("debug",debug);
        }
        headerQueue.convertAndSend(messageBuilder.build());
        return "Success Header Exchange";
    }
}