package com.farfarcoder.mcp.service;

import com.farfarcoder.mcp.data.CicdDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CicdToolService {
    private final CicdDataService cicdDataService;

    @Tool(description = "CICD 프로젝트 검색 - 프로젝트 키로 상세 정보 조회")
    public String searchProject(
            @ToolParam(description = "프로젝트 키 (예: LCTC)") String projectKey) {

        log.info("MCP Tool 호출: searchProject - {}", projectKey);
        return cicdDataService.searchProject(projectKey);
    }


    @Tool(description = "SCM API 서버 상태 확인 - 연결 상태 점검")
    public String checkApiStatus() {

        log.info("MCP Tool 호출: checkApiStatus");
        return cicdDataService.getApiStatus();
    }
}
