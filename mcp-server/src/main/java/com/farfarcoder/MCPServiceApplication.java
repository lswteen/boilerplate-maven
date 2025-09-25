package com.farfarcoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class MCPServiceApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(MCPServiceApplication.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port", "8091");
        String scmApiUrl = env.getProperty("scm.api.base-url", "http://localhost:58080");

        log.info("MCP Server 시작 완료!");
        log.info("MCP Server Port: {}", port);
        log.info("SCM API URL: {}", scmApiUrl);
        log.info(" 사용 가능한 도구:");
        log.info(" - searchProject: 프로젝트 검색");
        log.info(" - getBuildStatus: 빌드 상태 조회");
        log.info(" - getBuildHistory: 빌드 히스토리 조회");
        log.info(" - checkApiStatus: API 서버 상태 확인");
    }
}
