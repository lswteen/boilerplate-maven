package com.farfarcoder.scm.domain.bamboo.model;

import lombok.Builder;

import java.util.Collection;

@Builder(toBuilder = true)
public record Project(
        Long projectId,
        String projectKey,
        String title,
        String description,
        ProjMgmt projMgmt,
        Collection<Build> builds
) {
}
