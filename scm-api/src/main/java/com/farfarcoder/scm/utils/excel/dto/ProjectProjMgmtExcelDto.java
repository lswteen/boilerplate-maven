package com.farfarcoder.scm.utils.excel.dto;

import com.farfarcoder.scm.utils.excel.annotation.ExcelColumn;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectProjMgmtResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectProjMgmtExcelDto {
    @ExcelColumn(headerName = "프로젝트ID", order = 1)
    private Long projectId;

    @ExcelColumn(headerName = "프로젝트키", order = 2)
    private String projectKey;

    @ExcelColumn(headerName = "제목", order = 3)
    private String title;

    @ExcelColumn(headerName = "설명", order = 4)
    private String description;

    @ExcelColumn(headerName = "프로젝트관리ID", order = 5)
    private Long projMgmtId;

    @ExcelColumn(headerName = "사업부문", order = 6)
    private String bizDiv;

    @ExcelColumn(headerName = "사업담당자", order = 7)
    private String bizMgr;

    @ExcelColumn(headerName = "구성담당자", order = 8)
    private String config;

    @ExcelColumn(headerName = "개발담당자", order = 9)
    private String dev;

    @ExcelColumn(headerName = "운영담당자", order = 10)
    private String oper;

    @ExcelColumn(headerName = "상태", order = 11)
    private String status;

    @ExcelColumn(headerName = "Bitbucket명", order = 12)
    private String bitbucketName;

    @ExcelColumn(headerName = "Bitbucket키", order = 13)
    private String bitbucketKey;

    @ExcelColumn(headerName = "Bitbucket설명", order = 14)
    private String bitbucketDesc;

    @ExcelColumn(headerName = "Bamboo명", order = 15)
    private String bambooName;

    @ExcelColumn(headerName = "Bamboo키", order = 16)
    private String bambooKey;

    @ExcelColumn(headerName = "Bamboo설명", order = 17)
    private String bambooDesc;

    @ExcelColumn(headerName = "배포담당자", order = 18)
    private String deployMgr;

    @ExcelColumn(headerName = "생성일시", order = 19, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Instant createdAt;

    @ExcelColumn(headerName = "수정일시", order = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Instant updatedAt;

    /**
     * ProjectProjMgmtResponse를 Excel DTO로 변환
     */
    public static ProjectProjMgmtExcelDto from(ProjectProjMgmtResponse response) {
        return ProjectProjMgmtExcelDto.builder()
                .projectId(response.projectId())
                .projectKey(response.projectKey())
                .title(response.title())
                .description(response.description())
                .projMgmtId(response.projMgmtId())
                .bizDiv(response.bizDiv())
                .bizMgr(response.bizMgr())
                .config(response.config())
                .dev(response.dev())
                .oper(response.oper())
                .status(response.status())
                .bitbucketName(response.bitbucketName())
                .bitbucketKey(response.bitbucketKey())
                .bitbucketDesc(response.bitbucketDesc())
                .bambooName(response.bambooName())
                .bambooKey(response.bambooKey())
                .bambooDesc(response.bambooDesc())
                .deployMgr(response.deployMgr())
                .createdAt(response.createdAt())
                .updatedAt(response.updatedAt())
                .build();
    }
}
