package com.farfarcoder.scm.web.dashboard.controller.dto;

import com.farfarcoder.scm.utils.excel.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 매핑되지 않은 Project Excel 다운로드 전용 DTO
 * ProjMgmt와 매핑되지 않은 프로젝트 데이터 Excel 다운로드용
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnmappedProjectExcelDto {
    @ExcelColumn(headerName = "프로젝트ID", order = 1)
    private Long projectId;

    @ExcelColumn(headerName = "프로젝트키", order = 2)
    private String projectKey;

    @ExcelColumn(headerName = "제목", order = 3)
    private String title;

    @ExcelColumn(headerName = "설명", order = 4)
    private String description;

    @ExcelColumn(headerName = "매핑상태", order = 5)
    private String mappingStatus;

    @ExcelColumn(headerName = "비고", order = 6)
    private String remarks;

    /**
     * ProjectResponse를 Excel DTO로 변환
     */
    public static UnmappedProjectExcelDto from(ProjectResponse response) {
        return UnmappedProjectExcelDto.builder()
                .projectId(response.projectId())
                .projectKey(response.projectKey())
                .title(response.title())
                .description(response.description())
                .mappingStatus("매핑되지 않음")
                .remarks("ProjMgmt 테이블과 매핑이 필요한 프로젝트")
                .build();
    }
}
