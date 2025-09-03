package com.farfarcoder.scm.web.dashboard.service;

import com.farfarcoder.scm.utils.excel.dto.ProjMgmtExcelDto;
import com.farfarcoder.scm.utils.excel.dto.ProjectBuildExcelDto;
import com.farfarcoder.scm.utils.excel.dto.ProjectProjMgmtExcelDto;
import com.farfarcoder.scm.utils.excel.renderer.ExcelRenderer;

import com.farfarcoder.scm.web.dashboard.controller.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Excel 다운로드 전용 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelDownloadService {
    private final ExcelRenderer excelRenderer;

    /**
     * ProjectProjMgmt Excel 파일 생성 (Sheet1)
     */
    public ResponseEntity<Resource> generateProjectProjMgmtExcel(List<ProjectProjMgmtResponse> responses) {
        log.info("Starting ProjectProjMgmt Excel generation. Data count: {}", responses.size());

        try {
            // 1. Response를 Excel DTO로 변환
            List<ProjectProjMgmtExcelDto> excelData = responses.stream()
                    .map(ProjectProjMgmtExcelDto::from)
                    .collect(Collectors.toList());

            // 2. Excel 파일 생성 (우아한형제들 방식)
            byte[] excelBytes = excelRenderer.render(
                    excelData,
                    ProjectProjMgmtExcelDto.class,
                    "Project-ProjMgmt"
            );

            // 3. 파일명 생성 (날짜 포함)
            String fileName = generateFileName("project-projmgmt");

            // 4. ResponseEntity 생성
            return createExcelResponse(excelBytes, fileName);

        } catch (Exception e) {
            log.error("Failed to generate ProjectProjMgmt Excel file", e);
            throw new RuntimeException("Excel file generation failed", e);
        }
    }

    /**
     * ProjMgmt Excel 파일 생성 (Sheet2)
     * bambookey가 'NULL'인 프로젝트관리 데이터
     */
    public ResponseEntity<Resource> generateProjMgmtExcel(List<ProjMgmtDto> dtos) {
        log.info("Starting ProjMgmt Excel generation. Data count: {}", dtos.size());

        try {
            // 1. DTO를 Excel DTO로 변환
            List<ProjMgmtExcelDto> excelData = dtos.stream()
                    .map(ProjMgmtExcelDto::from)
                    .collect(Collectors.toList());

            // 2. Excel 파일 생성 (우아한형제들 방식)
            byte[] excelBytes = excelRenderer.render(
                    excelData,
                    ProjMgmtExcelDto.class,
                    "NULL-ProjMgmt"
            );

            // 3. 파일명 생성 (날짜 포함)
            String fileName = generateFileName("null-projmgmt");

            // 4. ResponseEntity 생성
            return createExcelResponse(excelBytes, fileName);

        } catch (Exception e) {
            log.error("Failed to generate ProjMgmt Excel file", e);
            throw new RuntimeException("Excel file generation failed", e);
        }
    }

    /**
     * ProjectBuild Excel 파일 생성 (Sheet3)
     * 프로젝트와 빌드 데이터 (중첩 구조를 평평하게 변환)
     */
    public ResponseEntity<Resource> generateProjectBuildExcel(List<ProjectBuildResponse> responses) {
        log.info("Starting ProjectBuild Excel generation. Data count: {}", responses.size());

        try {
            // 1. Response를 Excel DTO로 변환 (중첩 구조 평평화)
            List<ProjectBuildExcelDto> excelData = ProjectBuildExcelDto.fromResponseList(responses);

            log.info("Converted {} ProjectBuild responses to {} Excel rows",
                    responses.size(), excelData.size());

            // 2. Excel 파일 생성 (우아한형제들 방식)
            byte[] excelBytes = excelRenderer.render(
                    excelData,
                    ProjectBuildExcelDto.class,
                    "Project-Builds"
            );

            // 3. 파일명 생성 (날짜 포함)
            String fileName = generateFileName("project-builds");

            // 4. ResponseEntity 생성
            return createExcelResponse(excelBytes, fileName);

        } catch (Exception e) {
            log.error("Failed to generate ProjectBuild Excel file", e);
            throw new RuntimeException("Excel file generation failed", e);
        }
    }

    /**
     * Excel 파일명 생성 (현재 날짜 포함)
     */
    private String generateFileName(String prefix) {
        String currentDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        return String.format("%s-%s.xlsx", prefix, currentDate);
    }

    /**
     * Excel 다운로드 ResponseEntity 생성
     */
    private ResponseEntity<Resource> createExcelResponse(byte[] excelBytes, String fileName) {
        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE,
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .contentLength(excelBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    /**
     * Excel 파일 크기 검증 (선택사항)
     */
    private void validateExcelSize(byte[] excelBytes, int maxSizeMB) {
        int maxSizeBytes = maxSizeMB * 1024 * 1024;
        if (excelBytes.length > maxSizeBytes) {
            log.warn("Generated Excel file size ({} bytes) exceeds maximum size ({} bytes)",
                    excelBytes.length, maxSizeBytes);
            throw new RuntimeException("Excel file size too large");
        }
    }


    /**
     * 매핑되지 않은 Project Excel 파일 생성
     * ProjMgmt와 매핑되지 않은 프로젝트 데이터
     */
    public ResponseEntity<Resource> generateUnmappedProjectsExcel(List<ProjectResponse> responses) {
        log.info("Starting Unmapped Projects Excel generation. Data count: {}", responses.size());

        try {
            // 1. Response를 Excel DTO로 변환
            List<UnmappedProjectExcelDto> excelData = responses.stream()
                    .map(UnmappedProjectExcelDto::from)
                    .collect(Collectors.toList());

            // 2. Excel 파일 생성 (우아한형제들 방식)
            byte[] excelBytes = excelRenderer.render(
                    excelData,
                    UnmappedProjectExcelDto.class,
                    "Unmapped-Projects"
            );

            // 3. 파일명 생성 (날짜 포함)
            String fileName = generateFileName("unmapped-projects");

            // 4. ResponseEntity 생성
            return createExcelResponse(excelBytes, fileName);

        } catch (Exception e) {
            log.error("Failed to generate Unmapped Projects Excel file", e);
            throw new RuntimeException("Excel file generation failed", e);
        }
    }
}
