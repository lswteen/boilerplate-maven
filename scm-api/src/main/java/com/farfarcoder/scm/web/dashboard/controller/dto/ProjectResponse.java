package com.farfarcoder.scm.web.dashboard.controller.dto;

import com.farfarcoder.scm.domain.bamboo.model.Build;
import com.farfarcoder.scm.domain.bamboo.model.ProjMgmt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Collection;

@Builder(toBuilder = true)
public record ProjectResponse (
        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,
        @Schema(description = "프로젝트 키", example = "SAMPLE")
        String projectKey,
        @Schema(description = "프로젝트 제목", example = "Sample Project")
        String title,
        @Schema(description = "프로젝트 설명", example = "This is a sample project")
        String description,
        @Schema(description = "", example = "")
        ProjMgmt projMgmt,
        @Schema(description = "", example = "")
        Collection<Build> builds
){
}
