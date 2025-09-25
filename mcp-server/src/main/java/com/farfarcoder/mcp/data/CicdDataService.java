package com.farfarcoder.mcp.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CicdDataService{
    private final ScmApiClient scmApiClient;

    public String searchProject(String projectKey) {
        log.info("프로젝트 검색 요청: {}", projectKey);

        // 입력값 검증
        if (projectKey == null || projectKey.trim().isEmpty()) {
            return "프로젝트 키가 필요합니다.";
        }

        // 대문자 변환 및 공백 제거
        String cleanProjectKey = projectKey.trim().toUpperCase();

        // SCM API 호출
        return scmApiClient.searchProject(cleanProjectKey);
    }


    public String getApiStatus() {
        log.info("🔍 SCM API 상태 확인");

        boolean isHealthy = scmApiClient.healthCheck();
        if (isHealthy) {
            return "SCM API 서버가 정상 작동 중입니다.";
        } else {
            return "SCM API 서버에 연결할 수 없습니다.";
        }
    }
}
