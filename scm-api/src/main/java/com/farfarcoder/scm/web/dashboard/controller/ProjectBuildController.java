package com.farfarcoder.scm.web.dashboard.controller;

import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectBuildResponse;
import com.farfarcoder.scm.web.dashboard.service.ProjectBuildAppService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Project-Build API", description = "프로젝트와 빌드 조회 API")
@RestController
@RequestMapping("/api/v1/project-builds")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, maxAge = 3600)
public class ProjectBuildController {
    private final ProjectBuildAppService projectBuildAppService;

    @Operation(
            summary = "모든 프로젝트-빌드 조회",
            description = "모든 Project와 Build 데이터를 조회합니다. 각 Project에 속한 Build 목록이 포함됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectBuildResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProjectBuildResponse>> findAllProjectBuilds() {
        log.info("Request to find all project builds");

        List<ProjectBuildResponse> responses = projectBuildAppService.findAllProjectsWithBuilds();
        log.info("Found {} project build records", responses.size());

        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "프로젝트 키로 프로젝트-빌드 조회",
            description = "특정 프로젝트 키로 Project와 Build 데이터를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectBuildResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 프로젝트를 찾을 수 없음")
    })
    @GetMapping("/by-project-key/{projectKey}")
    public ResponseEntity<ProjectBuildResponse> findProjectBuildsByProjectKey(
            @Parameter(
                    name = "projectKey",
                    description = "조회할 프로젝트 키",
                    example = "LCTC",
                    required = true
            )
            @PathVariable("projectKey") String projectKey) {

        // 간단한 유효성 검사
        if (projectKey == null || projectKey.trim().isEmpty()) {
            log.warn("ProjectKey is null or empty");
            return ResponseEntity.badRequest().build();
        }

        log.info("Request to find project builds by projectKey: {}", projectKey);

        return projectBuildAppService.findProjectWithBuildsByProjectKey(projectKey)
                .map(response -> {
                    log.info("Found project build for projectKey: {} with {} builds",
                            projectKey, response.builds().size());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("No project found for projectKey: {}", projectKey);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(
            summary = "프로젝트 ID로 프로젝트-빌드 조회",
            description = "특정 프로젝트 ID로 Project와 Build 데이터를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectBuildResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 프로젝트를 찾을 수 없음")
    })
    @GetMapping("/by-project-id/{projectId}")
    public ResponseEntity<ProjectBuildResponse> findProjectBuildsByProjectId(
            @Parameter(
                    name = "projectId",
                    description = "조회할 프로젝트 ID",
                    example = "9240593",
                    required = true
            )
            @PathVariable("projectId") Long projectId) {

        log.info("Request to find project builds by projectId: {}", projectId);

        return projectBuildAppService.findProjectWithBuildsByProjectId(projectId)
                .map(response -> {
                    log.info("Found project build for projectId: {} with {} builds",
                            projectId, response.builds().size());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("No project found for projectId: {}", projectId);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(
            summary = "프로젝트 키로 프로젝트-빌드 조회 (Query Parameter)",
            description = "Query Parameter로 특정 프로젝트 키의 Project와 Build 데이터를 조회합니다. projectKey가 없으면 전체 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectBuildResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProjectBuildResponse>> findProjectBuildsByQuery(
            @Parameter(
                    name = "projectKey",
                    description = "조회할 프로젝트 키 (선택사항)",
                    example = "LCTC"
            )
            @RequestParam(value = "projectKey", required = false) String projectKey) {

        log.info("Request to find project builds with query parameter - projectKey: {}",
                projectKey == null ? "ALL" : projectKey);

        if (projectKey != null && !projectKey.trim().isEmpty()) {
            // 특정 프로젝트 조회
            return projectBuildAppService.findProjectWithBuildsByProjectKey(projectKey.trim())
                    .map(response -> ResponseEntity.ok(List.of(response)))
                    .orElseGet(() -> ResponseEntity.ok(List.of()));
        } else {
            // 전체 조회
            List<ProjectBuildResponse> responses = projectBuildAppService.findAllProjectsWithBuilds();
            log.info("Found {} project build records", responses.size());
            return ResponseEntity.ok(responses);
        }
    }
}
