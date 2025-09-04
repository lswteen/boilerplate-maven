package com.farfarcoder.scm.domain.bamboo.mapper;

import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.model.SimpleProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SimpleProjectMapper {
    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "projectKey", target = "projectKey")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    SimpleProject toModel(ProjectEntity projectEntity);

    List<SimpleProject> toModelList(List<ProjectEntity> projectEntities);
}
