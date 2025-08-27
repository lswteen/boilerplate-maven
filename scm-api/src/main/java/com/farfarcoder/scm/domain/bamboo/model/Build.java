package com.farfarcoder.scm.domain.bamboo.model;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record Build(
        Long buildId,
        Long projectId,
        String buildTitle,
        String buildType,
        Instant createdAt,
        Instant updatedAt,
        Project projectEntity
) {
}
