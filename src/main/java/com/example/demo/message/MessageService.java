package com.example.demo.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final RabbitTemplate rabbitTemplate;

    public MessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(MessagingConfig.topicExchangeName, "foo.bar.baz", message);
    }
}
