package com.farfarcoder.scm.web.dashboard.mapper;

import com.farfarcoder.scm.domain.bamboo.model.Build;
import com.farfarcoder.scm.domain.bamboo.model.ProjectBuild;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectBuildResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProjectBuildWebMapper {
    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "projectKey", target = "projectKey")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "builds", target = "builds")
    ProjectBuildResponse toResponse(ProjectBuild projectBuild);

    List<ProjectBuildResponse> toResponseList(List<ProjectBuild> projectBuilds);

    @Mapping(source = "buildId", target = "buildId")
    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "buildTitle", target = "buildTitle")
    @Mapping(source = "buildType", target = "buildType")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    ProjectBuildResponse.BuildResponse toBuildResponse(Build build);
}
