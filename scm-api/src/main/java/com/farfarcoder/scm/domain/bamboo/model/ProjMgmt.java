package com.farfarcoder.scm.domain.bamboo.model;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record ProjMgmt(
        Long id,
//        Long projectId,
        String bizDev,
        String bizMgr,
        String config,
        String dev,
        String oper,
        String status,
        String bitbucketName,
        String bitbucketKey,
        String bambooName,
        String bambooKey,
        String bambooDesc,
        String deployMgr,
        Instant createdAt,
        Instant updatedAt,
        Project projectEntity
) {
}
