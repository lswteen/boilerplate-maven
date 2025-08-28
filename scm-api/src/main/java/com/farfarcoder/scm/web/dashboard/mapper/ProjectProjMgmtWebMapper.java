package com.farfarcoder.scm.web.dashboard.mapper;

import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectProjMgmtResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProjectProjMgmtWebMapper {
    ProjectProjMgmtResponse toResponse(ProjectProjMgmt projectProjMgmt);
    List<ProjectProjMgmtResponse> toResponseList(List<ProjectProjMgmt> projectProjMgmts);
}
