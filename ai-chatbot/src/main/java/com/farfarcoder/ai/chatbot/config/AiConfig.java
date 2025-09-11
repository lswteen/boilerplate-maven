package com.farfarcoder.ai.chatbot.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    /**
     * ChatClient 빈 등록 (Claude 사용)
     * Spring AI의 고수준 API로 fluent interface 제공
     */
    @Bean
    public ChatClient chatClient(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel)
                .defaultSystem("""
                    당신은 CI/CD 관리를 전문으로 하는 AI 어시스턴트입니다.
                    
                    전문 분야:
                    - Jenkins, GitLab CI/CD, GitHub Actions 파이프라인
                    - Docker 컨테이너화 및 Kubernetes 배포
                    - Maven, Gradle 빌드 도구
                    - AWS, Azure, GCP 클라우드 배포
                    - DevOps 모범 사례 및 자동화
                    
                    응답 방식:
                    - 친절하고 정확한 답변 제공 (한국어로)
                    - 구체적인 예제 코드 포함
                    - 단계별로 명확하게 설명
                    - 실용적이고 현실적인 조언
                    """)
                .build();
    }
}
