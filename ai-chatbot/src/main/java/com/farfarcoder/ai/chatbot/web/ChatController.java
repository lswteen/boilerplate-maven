package com.farfarcoder.ai.chatbot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatClient chatClient;

    /**
     * 연결 상태 확인
     */
    @GetMapping("/health")
    public Map<String, Object> healthCheck() {
        try {
            ChatResponse response = chatClient
                    .prompt()
                    .user("Hello, respond with 'Spring AI is working!'")
                    .call()
                    .chatResponse();

            return Map.of(
                    "status", "healthy",
                    "message", "Spring AI 연결 정상",
                    "test_response", response.getResult().getOutput().getText(), // getText() -> getContent()
                    "model", response.getMetadata().getModel(),
                    "timestamp", System.currentTimeMillis()
            );

        } catch (Exception e) {
            return Map.of(
                    "status", "unhealthy",
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()
            );
        }
    }

    /**
     * 간단한 채팅 테스트
     */
    @PostMapping("/simple")
    public Map<String, Object> simpleChat(@RequestBody Map<String, String> request) {
        try {
            String userMessage = request.get("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return Map.of("error", "메시지가 비어있습니다.", "status", "error");
            }

            String response = chatClient
                    .prompt()
                    .user(userMessage)
                    .call()
                    .content();

            return Map.of(
                    "user_message", userMessage,
                    "ai_response", response,
                    "status", "success",
                    "timestamp", System.currentTimeMillis()
            );

        } catch (Exception e) {
            return Map.of(
                    "error", "AI 호출 중 오류 발생: " + e.getMessage(),
                    "status", "error",
                    "timestamp", System.currentTimeMillis()
            );
        }
    }

    /**
     * 시스템 프롬프트 테스트
     */
    @PostMapping("/system")
    public Map<String, Object> systemChat(@RequestBody Map<String, String> request) {
        try {
            String userMessage = request.get("message");
            String systemPrompt = request.getOrDefault("system",
                    "당신은 CI/CD 전문가입니다. Jenkins, Docker, Kubernetes에 대해 친절하게 설명해주세요.");

            String response = chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(userMessage)
                    .call()
                    .content();

            return Map.of(
                    "user_message", userMessage,
                    "system_prompt", systemPrompt,
                    "ai_response", response,
                    "status", "success",
                    "timestamp", System.currentTimeMillis()
            );

        } catch (Exception e) {
            return Map.of(
                    "error", "AI 호출 중 오류 발생: " + e.getMessage(),
                    "status", "error",
                    "timestamp", System.currentTimeMillis()
            );
        }
    }
}