package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties("mail")
public class MailProperties {

    private final String destination;

    public MailProperties(@DefaultValue("nobody@example.com") String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }
}
