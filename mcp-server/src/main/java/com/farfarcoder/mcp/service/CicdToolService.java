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
    public String searchProject(@ToolParam(description="프로젝트 키 (예: LCTC)") String projectKey) {
        log.info("🔍 MCP Tool called: searchProject with key={}", projectKey);
        return cicdDataService.searchProject(projectKey);
    }

    @Tool(description = "프로젝트 빌드 상태 조회 - 최근 빌드 정보 확인")
    public String getBuildStatus(@ToolParam(description="프로젝트 키 (예: LCTC)") String projectKey) {
        log.info("🔧 MCP Tool called: getBuildStatus with key={}", projectKey);
        return cicdDataService.getBuildStatus(projectKey);
    }
}
