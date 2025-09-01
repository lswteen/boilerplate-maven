package com.farfarcoder.scm.domain.bamboo.model;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record ProjectBuild(
        // Project 정보
        Long projectId,
        String projectKey,
        String title,
        String description,

        // Build 정보 (List)
        List<Build> builds
) {
}
