package com.farfarcoder.ai.chatbot.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    /**
     * ChatClient 빈 등록
     * Spring AI의 고수준 API로 fluent interface 제공
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("당신은 CI/CD 관리를 도와주는 전문 AI 어시스턴트입니다. 친절하고 정확한 답변을 제공해주세요.")
                .build();
    }
}
