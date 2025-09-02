package com.farfarcoder.scm.web.dashboard.controller;

import com.farfarcoder.scm.domain.bamboo.service.NullProjMgmtService;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjMgmtDto;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectBuildResponse;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectProjMgmtResponse;
import com.farfarcoder.scm.web.dashboard.service.ExcelDownloadService;
import com.farfarcoder.scm.web.dashboard.service.ProjectBuildAppService;
import com.farfarcoder.scm.web.dashboard.service.ProjectProjMgmtAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "Excel Download API", description = "프로젝트 데이터 Excel 다운로드 API")
@RestController
@RequestMapping("/api/v1/excel")
@RequiredArgsConstructor
public class ExcelDownloadController {
    // 클래스 상단의 기존 필드에 추가
    private final ExcelDownloadService excelDownloadService;
    private final ProjectProjMgmtAppService projectProjMgmtAppService;
    private final NullProjMgmtService nullProjMgmtService;
    private final ProjectBuildAppService projectBuildAppService; // 기존

    /**
     * ProjectProjMgmt Excel 다운로드 API
     */
    @Operation(
            summary = "프로젝트-프로젝트관리 Excel 다운로드",
            description = "매핑된 모든 Project와 ProjMgmt 데이터를 Excel 파일로 다운로드합니다. " +
                    "파일명: project-projmgmt-{yyyyMMdd-HHmmss}.xlsx"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel 파일 다운로드 성공",
                    content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")),
            @ApiResponse(responseCode = "500", description = "Excel 파일 생성 실패")
    })
    @GetMapping("/projectWithProjMgmt")
    public ResponseEntity<Resource> downloadProjectWithProjMgmtExcel() {
        try {
            // 1. 기존 API와 동일한 데이터 조회
            List<ProjectProjMgmtResponse> responses = projectProjMgmtAppService.findAllProjectsWithProjMgmt();
            log.info("Retrieved {} records for Excel download", responses.size());
            // 2. Excel 파일 생성 및 다운로드 응답
            ResponseEntity<Resource> excelResponse = excelDownloadService.generateProjectProjMgmtExcel(responses);
            log.info("ProjectProjMgmt Excel download completed successfully");
            return excelResponse;
        } catch (Exception e) {
            log.error("Failed to download ProjectProjMgmt Excel file", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 4. Excel 다운로드 메소드 추가
    @Operation(
            summary = "bambookey가 'NULL'인 프로젝트관리 Excel 다운로드",
            description = "ProjMgmt 테이블에서 bambookey가 'NULL' 문자열인 데이터를 Excel 파일로 다운로드합니다. " +
                    "파일명: null-projmgmt-{yyyyMMdd-HHmmss}.xlsx"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel 파일 다운로드 성공",
                    content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")),
            @ApiResponse(responseCode = "500", description = "Excel 파일 생성 실패")
    })
    @GetMapping("/projMgmtByNull")
    public ResponseEntity<Resource> downloadProjMgmtByNullExcel() {
        try {
            // 1. 기존 DTO API와 동일한 데이터 조회 (N+1 해결된 버전)
            List<ProjMgmtDto> dtos = nullProjMgmtService.findNullBambooKeyProjMgmtAsDto();
            log.info("Retrieved {} NULL ProjMgmt records for Excel download", dtos.size());

            // 2. Excel 파일 생성 및 다운로드 응답
            ResponseEntity<Resource> excelResponse = excelDownloadService.generateProjMgmtExcel(dtos);
            log.info("NULL ProjMgmt Excel download completed successfully");
            return excelResponse;
        } catch (Exception e) {
            log.error("Failed to download NULL ProjMgmt Excel file", e);
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(
            summary = "프로젝트-빌드 Excel 다운로드",
            description = "모든 Project와 Build 데이터를 Excel 파일로 다운로드합니다. " +
                    "중첩된 구조(프로젝트 1개 : 빌드 N개)를 평평하게 변환하여 " +
                    "각 빌드마다 별도의 행으로 표시합니다. " +
                    "파일명: project-builds-{yyyyMMdd-HHmmss}.xlsx"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel 파일 다운로드 성공",
                    content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")),
            @ApiResponse(responseCode = "500", description = "Excel 파일 생성 실패")
    })
    @GetMapping("/projectWithBuilds")
    public ResponseEntity<Resource> downloadProjectWithBuilds() {
        log.info("Request to download ProjectBuild Excel file");

        try {
            // 1. 기존 API와 동일한 데이터 조회
            List<ProjectBuildResponse> responses = projectBuildAppService.findAllProjectsWithBuilds();

            log.info("Retrieved {} ProjectBuild records for Excel download", responses.size());

            // 2. Excel 파일 생성 및 다운로드 응답
            ResponseEntity<Resource> excelResponse = excelDownloadService.generateProjectBuildExcel(responses);

            log.info("ProjectBuild Excel download completed successfully");
            return excelResponse;

        } catch (Exception e) {
            log.error("Failed to download ProjectBuild Excel file", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}