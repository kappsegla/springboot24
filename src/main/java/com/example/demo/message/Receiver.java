package com.example.demo.message;

import com.example.demo.MailSenderService;
import com.example.demo.config.MailProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private final MailSenderService mailSenderService;
    MailProperties mailProperties;

    public Receiver(MailSenderService mailSenderService, MailProperties mailProperties) {
        this.mailSenderService = mailSenderService;
        this.mailProperties = mailProperties;
    }

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
         mailSenderService.sendNewMail(mailProperties.getDestination(),"Cat created", message);
    }
}
