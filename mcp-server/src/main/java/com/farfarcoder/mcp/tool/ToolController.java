package com.farfarcoder.mcp.tool;

import com.farfarcoder.mcp.service.CicdToolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
@Slf4j
public class ToolController {
    private final CicdToolService cicdToolService;

    @PostMapping("/searchProject")
    public ResponseEntity<String> searchProject(@RequestBody Map<String, String> request) {
        String projectKey = request.get("projectKey");
        log.info("HTTP API 호출: searchProject - {}", projectKey);
        String result = cicdToolService.searchProject(projectKey);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/checkApiStatus")
    public ResponseEntity<String> checkApiStatus() {
        log.info("HTTP API 호출: checkApiStatus");
        String result = cicdToolService.checkApiStatus();
        return ResponseEntity.ok(result);
    }

}
