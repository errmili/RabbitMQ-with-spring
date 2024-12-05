package com.spring.rabbitmq.model;

import java.time.LocalDateTime;

public class MessageDto {
    private String status;
    private LocalDateTime localDateTime;
    public MessageDto(String status, LocalDateTime localDateTime) {
        this.status = status;
        this.localDateTime = localDateTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
