package com.farfarcoder.scm.domain.bamboo.mapper;

import com.farfarcoder.scm.domain.bamboo.entity.ProjMgmtEntity;
import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T16:52:03+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class ProjectProjMgmtMapperImpl implements ProjectProjMgmtMapper {

    @Override
    public ProjectProjMgmt toModel(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }

        ProjectProjMgmt.ProjectProjMgmtBuilder projectProjMgmt = ProjectProjMgmt.builder();

        projectProjMgmt.projectId( projectEntity.getProjectId() );
        projectProjMgmt.projectKey( projectEntity.getProjectKey() );
        projectProjMgmt.title( projectEntity.getTitle() );
        projectProjMgmt.description( projectEntity.getDescription() );
        projectProjMgmt.projMgmtId( projectEntityProjMgmtId( projectEntity ) );
        projectProjMgmt.bizDiv( projectEntityProjMgmtBizDiv( projectEntity ) );
        projectProjMgmt.bizMgr( projectEntityProjMgmtBizMgr( projectEntity ) );
        projectProjMgmt.config( projectEntityProjMgmtConfig( projectEntity ) );
        projectProjMgmt.dev( projectEntityProjMgmtDev( projectEntity ) );
        projectProjMgmt.oper( projectEntityProjMgmtOper( projectEntity ) );
        projectProjMgmt.status( projectEntityProjMgmtStatus( projectEntity ) );
        projectProjMgmt.bitbucketName( projectEntityProjMgmtBitbucketName( projectEntity ) );
        projectProjMgmt.bitbucketKey( projectEntityProjMgmtBitbucketKey( projectEntity ) );
        projectProjMgmt.bitbucketDesc( projectEntityProjMgmtBitbucketDesc( projectEntity ) );
        projectProjMgmt.bambooName( projectEntityProjMgmtBambooName( projectEntity ) );
        projectProjMgmt.bambooKey( projectEntityProjMgmtBambooKey( projectEntity ) );
        projectProjMgmt.bambooDesc( projectEntityProjMgmtBambooDesc( projectEntity ) );
        projectProjMgmt.deployMgr( projectEntityProjMgmtDeployMgr( projectEntity ) );
        projectProjMgmt.createdAt( projectEntityProjMgmtCreatedAt( projectEntity ) );
        projectProjMgmt.updatedAt( projectEntityProjMgmtUpdatedAt( projectEntity ) );

        return projectProjMgmt.build();
    }

    @Override
    public List<ProjectProjMgmt> toModelList(List<ProjectEntity> projectEntities) {
        if ( projectEntities == null ) {
            return null;
        }

        List<ProjectProjMgmt> list = new ArrayList<ProjectProjMgmt>( projectEntities.size() );
        for ( ProjectEntity projectEntity : projectEntities ) {
            list.add( toModel( projectEntity ) );
        }

        return list;
    }

    private Long projectEntityProjMgmtId(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        Long id = projMgmt.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String projectEntityProjMgmtBizDiv(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String bizDiv = projMgmt.getBizDiv();
        if ( bizDiv == null ) {
            return null;
        }
        return bizDiv;
    }

    private String projectEntityProjMgmtBizMgr(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String bizMgr = projMgmt.getBizMgr();
        if ( bizMgr == null ) {
            return null;
        }
        return bizMgr;
    }

    private String projectEntityProjMgmtConfig(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String config = projMgmt.getConfig();
        if ( config == null ) {
            return null;
        }
        return config;
    }

    private String projectEntityProjMgmtDev(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String dev = projMgmt.getDev();
        if ( dev == null ) {
            return null;
        }
        return dev;
    }

    private String projectEntityProjMgmtOper(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String oper = projMgmt.getOper();
        if ( oper == null ) {
            return null;
        }
        return oper;
    }

    private String projectEntityProjMgmtStatus(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String status = projMgmt.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }

    private String projectEntityProjMgmtBitbucketName(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String bitbucketName = projMgmt.getBitbucketName();
        if ( bitbucketName == null ) {
            return null;
        }
        return bitbucketName;
    }

    private String projectEntityProjMgmtBitbucketKey(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String bitbucketKey = projMgmt.getBitbucketKey();
        if ( bitbucketKey == null ) {
            return null;
        }
        return bitbucketKey;
    }

    private String projectEntityProjMgmtBitbucketDesc(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String bitbucketDesc = projMgmt.getBitbucketDesc();
        if ( bitbucketDesc == null ) {
            return null;
        }
        return bitbucketDesc;
    }

    private String projectEntityProjMgmtBambooName(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String bambooName = projMgmt.getBambooName();
        if ( bambooName == null ) {
            return null;
        }
        return bambooName;
    }

    private String projectEntityProjMgmtBambooKey(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String bambooKey = projMgmt.getBambooKey();
        if ( bambooKey == null ) {
            return null;
        }
        return bambooKey;
    }

    private String projectEntityProjMgmtBambooDesc(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String bambooDesc = projMgmt.getBambooDesc();
        if ( bambooDesc == null ) {
            return null;
        }
        return bambooDesc;
    }

    private String projectEntityProjMgmtDeployMgr(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        String deployMgr = projMgmt.getDeployMgr();
        if ( deployMgr == null ) {
            return null;
        }
        return deployMgr;
    }

    private Instant projectEntityProjMgmtCreatedAt(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        Instant createdAt = projMgmt.getCreatedAt();
        if ( createdAt == null ) {
            return null;
        }
        return createdAt;
    }

    private Instant projectEntityProjMgmtUpdatedAt(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        ProjMgmtEntity projMgmt = projectEntity.getProjMgmt();
        if ( projMgmt == null ) {
            return null;
        }
        Instant updatedAt = projMgmt.getUpdatedAt();
        if ( updatedAt == null ) {
            return null;
        }
        return updatedAt;
    }
}
