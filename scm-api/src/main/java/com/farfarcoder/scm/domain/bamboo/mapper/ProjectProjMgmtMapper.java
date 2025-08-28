package com.farfarcoder.scm.domain.bamboo.mapper;

import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProjectProjMgmtMapper {
    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "projectKey", target = "projectKey")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "projMgmt.id", target = "projMgmtId")
    @Mapping(source = "projMgmt.bizDiv", target = "bizDiv")
    @Mapping(source = "projMgmt.bizMgr", target = "bizMgr")
    @Mapping(source = "projMgmt.config", target = "config")
    @Mapping(source = "projMgmt.dev", target = "dev")
    @Mapping(source = "projMgmt.oper", target = "oper")
    @Mapping(source = "projMgmt.status", target = "status")
    @Mapping(source = "projMgmt.bitbucketName", target = "bitbucketName")
    @Mapping(source = "projMgmt.bitbucketKey", target = "bitbucketKey")
    @Mapping(source = "projMgmt.bitbucketDesc", target = "bitbucketDesc")
    @Mapping(source = "projMgmt.bambooName", target = "bambooName")
    @Mapping(source = "projMgmt.bambooKey", target = "bambooKey")
    @Mapping(source = "projMgmt.bambooDesc", target = "bambooDesc")
    @Mapping(source = "projMgmt.deployMgr", target = "deployMgr")
    @Mapping(source = "projMgmt.createdAt", target = "createdAt")
    @Mapping(source = "projMgmt.updatedAt", target = "updatedAt")
    ProjectProjMgmt toModel(ProjectEntity projectEntity);

    List<ProjectProjMgmt> toModelList(List<ProjectEntity> projectEntities);
}
