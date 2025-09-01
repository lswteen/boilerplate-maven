package com.farfarcoder.scm.web.dashboard.controller.dto;

import lombok.Builder;

import java.time.Instant;

/**
 * ProjMgmt DTO - Entity 매핑 관계 없이 순수 데이터만 전송
 */
@Builder(toBuilder = true)
public record ProjMgmtDto (
        Long id,
        String bizDiv,
        String bizMgr,
        String config,
        String dev,
        String oper,
        String status,
        String bitbucketName,
        String bitbucketKey,
        String bitbucketDesc,
        String bambooName,
        String bambooKey,
        String bambooDesc,
        String deployMgr,
        Instant createdAt,
        Instant updatedAt
){
}
