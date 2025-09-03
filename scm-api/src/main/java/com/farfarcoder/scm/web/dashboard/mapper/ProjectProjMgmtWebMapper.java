package com.farfarcoder.scm.web.dashboard.mapper;

import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import com.farfarcoder.scm.domain.bamboo.model.SimpleProject;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectProjMgmtResponse;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectProjMgmtWebMapper {
    ProjectProjMgmtResponse toResponse(ProjectProjMgmt projectProjMgmt);
    List<ProjectProjMgmtResponse> toResponseList(List<ProjectProjMgmt> projectProjMgmts);

    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "projectKey", target = "projectKey")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    ProjectResponse toResponse(SimpleProject simpleProjects);

    List<ProjectResponse> toProjectResponseList(List<SimpleProject> simpleProjects);
}
