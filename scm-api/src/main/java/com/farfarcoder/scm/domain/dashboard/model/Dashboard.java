package com.farfarcoder.scm.domain.dashboard.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Dashboard(
        Integer levelType,
        Long projectId,
        String projectKey,
        String title,
        String description,
        Long buildId,
        String buildTitle,
        String buildType,
        Long buildSeq,
        // PROJMGMT 필드들 추가
        String bambooKey,
        String bizDiv,
        String bizMgr,
        String config,
        String dev,
        String oper,
        String status,
        String bitbucketName,
        String bitbucketKey,
        String bitbucketDesc,
        String bambooDesc,
        String deployMgr
) {
}
