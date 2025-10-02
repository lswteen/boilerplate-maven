package com.farfarcoder.mcp.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScmApiClient {
    private final RestClient restClient;

    @Value("${scm.api.base-url}")
    private String baseUrl;

    public String searchProject(String projectKey) {
        try {
            log.info("SCM API 호출: 프로젝트 검색 - {}", projectKey);

            String response = restClient.get()
                    .uri(baseUrl + "/api/v1/project-builds/by-project-key/" + projectKey)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), (request, response1) -> {
                        log.warn("프로젝트를 찾을 수 없습니다: {}", projectKey);
                        throw new RestClientException("프로젝트를 찾을 수 없습니다: " + projectKey);
                    })
                    .body(String.class);

            log.debug("프로젝트 검색 성공: {}", projectKey);
            return response != null ? response : "프로젝트 정보 없음";

        } catch (RestClientException e) {
            log.error("프로젝트 검색 실패: {}", e.getMessage());
            return "프로젝트 검색 중 오류 발생: " + e.getMessage();
        }
    }

    /**
     * SCM API 서버 상태 확인
     */
    public boolean healthCheck() {
        try {
            log.info("SCM API 헬스체크");

            String response = restClient.get()
                    .uri(baseUrl + "/management/health")
                    .retrieve()
                    .body(String.class);

            boolean isHealthy = response != null && response.contains("UP");
            log.info("SCM API 헬스체크 결과: {}", isHealthy ? "정상" : "비정상");
            return isHealthy;

        } catch (Exception e) {
            log.error("SCM API 헬스체크 실패: {}", e.getMessage());
            return false;
        }
    }
}
