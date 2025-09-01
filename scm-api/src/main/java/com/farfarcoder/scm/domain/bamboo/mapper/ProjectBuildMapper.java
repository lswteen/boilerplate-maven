package com.farfarcoder.scm.domain.bamboo.mapper;

import com.farfarcoder.scm.domain.bamboo.entity.BuildEntity;
import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.model.Build;
import com.farfarcoder.scm.domain.bamboo.model.ProjectBuild;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProjectBuildMapper {
    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "projectKey", target = "projectKey")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "builds", target = "builds", qualifiedByName = "mapBuilds")
    ProjectBuild toModel(ProjectEntity projectEntity);

    List<ProjectBuild> toModelList(List<ProjectEntity> projectEntities);

    @Named("mapBuilds")
    default List<Build> mapBuilds(Collection<BuildEntity> buildEntities) {
        if (buildEntities == null) {
            return List.of();
        }
        return buildEntities.stream()
                .map(this::buildEntityToBuild)
                .collect(Collectors.toList());
    }

    @Mapping(source = "buildId", target = "buildId")
    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "buildTitle", target = "buildTitle")
    @Mapping(source = "buildType", target = "buildType")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(target = "projectEntity", ignore = true) // 순환참조 방지
    Build buildEntityToBuild(BuildEntity buildEntity);
}
