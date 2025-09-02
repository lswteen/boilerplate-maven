package com.farfarcoder.scm.utils.excel.dto;

import com.farfarcoder.scm.utils.excel.annotation.ExcelColumn;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectBuildResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectBuild Excel 다운로드 전용 DTO (Sheet3)
 * ProjectBuildResponse의 중첩 구조를 평평하게 변환하여 Excel 출력에 적합하게 만듦
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBuildExcelDto {

    // Project 정보
    @ExcelColumn(headerName = "프로젝트ID", order = 1)
    private Long projectId;

    @ExcelColumn(headerName = "프로젝트키", order = 2)
    private String projectKey;

    @ExcelColumn(headerName = "프로젝트제목", order = 3)
    private String projectTitle;

    @ExcelColumn(headerName = "프로젝트설명", order = 4)
    private String projectDescription;

    // Build 정보
    @ExcelColumn(headerName = "빌드ID", order = 5)
    private Long buildId;

    @ExcelColumn(headerName = "빌드제목", order = 6)
    private String buildTitle;

    @ExcelColumn(headerName = "빌드타입", order = 7)
    private String buildType;

    @ExcelColumn(headerName = "빌드생성일시", order = 8, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Instant buildCreatedAt;

    @ExcelColumn(headerName = "빌드수정일시", order = 9, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Instant buildUpdatedAt;

    /**
     * ProjectBuildResponse를 Excel DTO List로 변환
     * 중첩된 구조(Project + List<Build>)를 평평한 구조로 변환
     */
    public static List<ProjectBuildExcelDto> fromResponseList(List<ProjectBuildResponse> responses) {
        List<ProjectBuildExcelDto> excelDtos = new ArrayList<>();

        for (ProjectBuildResponse response : responses) {
            if (response.builds() == null || response.builds().isEmpty()) {
                // 빌드가 없는 프로젝트는 빌드 정보를 null로 하여 한 행으로 표시
                excelDtos.add(createExcelDto(response, null));
            } else {
                // 각 빌드마다 별도의 행으로 생성
                for (ProjectBuildResponse.BuildResponse build : response.builds()) {
                    excelDtos.add(createExcelDto(response, build));
                }
            }
        }

        return excelDtos;
    }

    /**
     * 개별 ProjectBuild + Build 조합으로 Excel DTO 생성
     */
    private static ProjectBuildExcelDto createExcelDto(ProjectBuildResponse project,
                                                       ProjectBuildResponse.BuildResponse build) {
        ProjectBuildExcelDtoBuilder builder = ProjectBuildExcelDto.builder()
                .projectId(project.projectId())
                .projectKey(project.projectKey())
                .projectTitle(project.title())
                .projectDescription(project.description());

        if (build != null) {
            builder.buildId(build.buildId())
                    .buildTitle(build.buildTitle())
                    .buildType(build.buildType())
                    .buildCreatedAt(build.createdAt())
                    .buildUpdatedAt(build.updatedAt());
        }

        return builder.build();
    }
}
