package com.farfarcoder.scm.domain.bamboo.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record SimpleProject (
        Long projectId,
        String projectKey,
        String title,
        String description
){
}
