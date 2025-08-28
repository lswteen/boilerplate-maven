package com.farfarcoder.scm.domain.bamboo.model;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record ProjectProjMgmt(
        // Project 필드
        Long projectId,
        String projectKey,
        String title,
        String description,

        // ProjMgmt 필드
        Long projMgmtId,
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
