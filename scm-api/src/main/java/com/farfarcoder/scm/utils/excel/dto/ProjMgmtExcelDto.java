package com.farfarcoder.scm.utils.excel.dto;

import com.farfarcoder.scm.utils.excel.annotation.ExcelColumn;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjMgmtDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * ProjMgmt Excel 다운로드 전용 DTO (Sheet2)
 * bambookey가 'NULL'인 프로젝트관리 데이터 Excel 다운로드용
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjMgmtExcelDto {
    @ExcelColumn(headerName = "ID", order = 1)
    private Long id;

    @ExcelColumn(headerName = "사업부문", order = 2)
    private String bizDiv;

    @ExcelColumn(headerName = "사업담당자", order = 3)
    private String bizMgr;

    @ExcelColumn(headerName = "구성담당자", order = 4)
    private String config;

    @ExcelColumn(headerName = "개발담당자", order = 5)
    private String dev;

    @ExcelColumn(headerName = "운영담당자", order = 6)
    private String oper;

    @ExcelColumn(headerName = "상태", order = 7)
    private String status;

    @ExcelColumn(headerName = "Bitbucket명", order = 8)
    private String bitbucketName;

    @ExcelColumn(headerName = "Bitbucket키", order = 9)
    private String bitbucketKey;

    @ExcelColumn(headerName = "Bitbucket설명", order = 10)
    private String bitbucketDesc;

    @ExcelColumn(headerName = "Bamboo명", order = 11)
    private String bambooName;

    @ExcelColumn(headerName = "Bamboo키", order = 12)
    private String bambooKey;

    @ExcelColumn(headerName = "Bamboo설명", order = 13)
    private String bambooDesc;

    @ExcelColumn(headerName = "배포담당자", order = 14)
    private String deployMgr;

    @ExcelColumn(headerName = "생성일시", order = 15, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Instant createdAt;

    @ExcelColumn(headerName = "수정일시", order = 16, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Instant updatedAt;

    /**
     * ProjMgmtDto를 Excel DTO로 변환
     */
    public static ProjMgmtExcelDto from(ProjMgmtDto dto) {
        return ProjMgmtExcelDto.builder()
                .id(dto.id())
                .bizDiv(dto.bizDiv())
                .bizMgr(dto.bizMgr())
                .config(dto.config())
                .dev(dto.dev())
                .oper(dto.oper())
                .status(dto.status())
                .bitbucketName(dto.bitbucketName())
                .bitbucketKey(dto.bitbucketKey())
                .bitbucketDesc(dto.bitbucketDesc())
                .bambooName(dto.bambooName())
                .bambooKey(dto.bambooKey())
                .bambooDesc(dto.bambooDesc())
                .deployMgr(dto.deployMgr())
                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt())
                .build();
    }
}
