package com.farfarcoder.scm.web.dashboard.controller;

import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectProjMgmtResponse;
import com.farfarcoder.scm.web.dashboard.service.ProjectProjMgmtAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "Project-ProjMgmt API", description = "프로젝트와 프로젝트 관리 매핑 조회 API")
@RestController
@RequestMapping("/api/v1/project-projmgmt")
@RequiredArgsConstructor
public class ProjectProjMgmtController {
    private final ProjectProjMgmtAppService projectProjMgmtAppService;

    @Operation(
            summary = "매핑된 모든 프로젝트-프로젝트관리 조회",
            description = "Project와 ProjMgmt가 매핑된 모든 데이터를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectProjMgmtResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProjectProjMgmtResponse>> findAllProjectsWithProjMgmt() {
        List<ProjectProjMgmtResponse> responses = projectProjMgmtAppService.findAllProjectsWithProjMgmt();
        log.info("=========> findAllProjectsWithProjMgmt count : {}", responses.size());
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "프로젝트 키로 매핑 조회",
            description = "특정 프로젝트 키로 Project와 ProjMgmt 매핑 데이터를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectProjMgmtResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 프로젝트를 찾을 수 없음")
    })
    @GetMapping("/by-project-key/{projectKey}")
    public ResponseEntity<ProjectProjMgmtResponse> findByProjectKey(
            @Parameter(
                    name = "projectKey",
                    description = "조회할 프로젝트 키",
                    example = "DEMO",
                    required = true
            )
            @PathVariable("projectKey") String projectKey) {

        // 간단한 유효성 검사
        if (projectKey == null || projectKey.trim().isEmpty()) {
            log.warn("ProjectKey is null or empty");
            return ResponseEntity.badRequest().build();
        }

        log.info("Request to find project with projmgmt by projectKey: {}", projectKey);

        return projectProjMgmtAppService.findProjectWithProjMgmtByProjectKey(projectKey)
                .map(response -> {
                    log.info("Found project with projmgmt for projectKey: {}", projectKey);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("No project found with projmgmt for projectKey: {}", projectKey);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(
            summary = "밤부 키로 매핑 조회",
            description = "특정 밤부 키로 Project와 ProjMgmt 매핑 데이터를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectProjMgmtResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 밤부 키를 찾을 수 없음")
    })
    @GetMapping("/by-bamboo-key/{bambooKey}")
    public ResponseEntity<ProjectProjMgmtResponse> findByBambooKey(
            @Parameter(
                    name = "bambooKey",
                    description = "조회할 밤부 키",
                    example = "DEMO",
                    required = true
            )
            @PathVariable("bambooKey") String bambooKey) {

        // 간단한 유효성 검사
        if (bambooKey == null || bambooKey.trim().isEmpty()) {
            log.warn("BambooKey is null or empty");
            return ResponseEntity.badRequest().build();
        }

        log.info("Request to find project with projmgmt by bambooKey: {}", bambooKey);

        return projectProjMgmtAppService.findProjectWithProjMgmtByBambooKey(bambooKey)
                .map(response -> {
                    log.info("Found project with projmgmt for bambooKey: {}", bambooKey);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("No project found with projmgmt for bambooKey: {}", bambooKey);
                    return ResponseEntity.notFound().build();
                });
    }
}
