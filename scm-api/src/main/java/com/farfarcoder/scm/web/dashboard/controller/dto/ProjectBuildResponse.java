package com.farfarcoder.scm.web.dashboard.controller.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

public record ProjectBuildResponse(
        // Project 정보
        Long projectId,
        String projectKey,
        String title,
        String description,

        // Build 정보 (List)
        List<BuildResponse> builds
) {
    @Builder(toBuilder = true)
    public record BuildResponse(
            Long buildId,
            Long projectId,
            String buildTitle,
            String buildType,
            Instant createdAt,
            Instant updatedAt
    ) {
    }
}
