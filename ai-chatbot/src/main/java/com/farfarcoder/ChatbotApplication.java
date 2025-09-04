package com.farfarcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatbotApplication {
    public static void main(String[] args) {
        System.out.println("🤖 Starting AI Chatbot for CICD Management...");
        SpringApplication.run(ChatbotApplication.class, args);
    }
}
