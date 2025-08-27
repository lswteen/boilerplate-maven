package com.farfarcoder.scm.web.dashboard.controller;

import com.farfarcoder.scm.web.dashboard.controller.dto.DashboardResponse;
import com.farfarcoder.scm.web.dashboard.service.DashboardAppService;
import com.farfarcoder.scm.web.dashboard.service.DashboardExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DashboardController {
    private final DashboardAppService dashboardAppService;
    private final DashboardExcelService dashboardExcelService;

    @Operation(summary = "dashboard 조회", description = "projectKey 또는 전체 조회 기능")
    @ApiResponse(responseCode = "200", description="조회 성공")
    @ApiResponse(responseCode = "400", description="잘못된 요청")
    @GetMapping("/dashboards")
    public ResponseEntity<List<DashboardResponse>> getDashboards(@RequestParam(value="projectKey",required = false, defaultValue = "") String projectKey) {
        return ResponseEntity.ok(dashboardAppService.findDashboards(projectKey));
    }


    @Operation(summary = "dashboard 조회(Path Variable)", description = "Path Variable 기반 projectKey 또는 전체 조회 기능")
    @ApiResponse(responseCode = "200", description="조회 성공")
    @ApiResponse(responseCode = "400", description="잘못된 요청")
    @GetMapping("/dashboards/{projectKey}")
    public ResponseEntity<List<DashboardResponse>> getPathDashboards(
            @Parameter(description = "Bamboo Project 프로젝트 키정보", example = "SCM")
            @PathVariable String projectKey) {

        return ResponseEntity.ok(dashboardAppService.findDashboards(projectKey));
    }

    @Operation(summary = "Dashboard Excel Download", description = "dashboard excel download")
    @ApiResponse(responseCode = "200", description="조회 성공")
    @ApiResponse(responseCode = "400", description="잘못된 요청")
    @GetMapping("/dashboards/excel")
    public ResponseEntity<byte[]> downloadProjectBuildsExcel(@RequestParam(value="projectkey", required = false, defaultValue = "") String projectKey) throws IOException {

        byte[] excelData = dashboardExcelService.exportDashboardToExcel(projectKey);
        String filename = generateDashboardFilename(projectKey);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }

    private String generateDashboardFilename(String projectKey) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String prefix = (projectKey != null && !projectKey.isEmpty())
                ? "Dashboard_" + projectKey + "_"
                : "Dashboard_All_";
        return prefix + timestamp + ".xlsx";
    }

}
