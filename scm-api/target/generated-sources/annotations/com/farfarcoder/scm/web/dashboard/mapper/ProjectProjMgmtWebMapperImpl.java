package com.farfarcoder.scm.web.dashboard.mapper;

import com.farfarcoder.scm.domain.bamboo.model.ProjectProjMgmt;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectProjMgmtResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-02T15:26:34+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class ProjectProjMgmtWebMapperImpl implements ProjectProjMgmtWebMapper {

    @Override
    public ProjectProjMgmtResponse toResponse(ProjectProjMgmt projectProjMgmt) {
        if ( projectProjMgmt == null ) {
            return null;
        }

        ProjectProjMgmtResponse.ProjectProjMgmtResponseBuilder projectProjMgmtResponse = ProjectProjMgmtResponse.builder();

        projectProjMgmtResponse.projectId( projectProjMgmt.projectId() );
        projectProjMgmtResponse.projectKey( projectProjMgmt.projectKey() );
        projectProjMgmtResponse.title( projectProjMgmt.title() );
        projectProjMgmtResponse.description( projectProjMgmt.description() );
        projectProjMgmtResponse.projMgmtId( projectProjMgmt.projMgmtId() );
        projectProjMgmtResponse.bizDiv( projectProjMgmt.bizDiv() );
        projectProjMgmtResponse.bizMgr( projectProjMgmt.bizMgr() );
        projectProjMgmtResponse.config( projectProjMgmt.config() );
        projectProjMgmtResponse.dev( projectProjMgmt.dev() );
        projectProjMgmtResponse.oper( projectProjMgmt.oper() );
        projectProjMgmtResponse.status( projectProjMgmt.status() );
        projectProjMgmtResponse.bitbucketName( projectProjMgmt.bitbucketName() );
        projectProjMgmtResponse.bitbucketKey( projectProjMgmt.bitbucketKey() );
        projectProjMgmtResponse.bitbucketDesc( projectProjMgmt.bitbucketDesc() );
        projectProjMgmtResponse.bambooName( projectProjMgmt.bambooName() );
        projectProjMgmtResponse.bambooKey( projectProjMgmt.bambooKey() );
        projectProjMgmtResponse.bambooDesc( projectProjMgmt.bambooDesc() );
        projectProjMgmtResponse.deployMgr( projectProjMgmt.deployMgr() );
        projectProjMgmtResponse.createdAt( projectProjMgmt.createdAt() );
        projectProjMgmtResponse.updatedAt( projectProjMgmt.updatedAt() );

        return projectProjMgmtResponse.build();
    }

    @Override
    public List<ProjectProjMgmtResponse> toResponseList(List<ProjectProjMgmt> projectProjMgmts) {
        if ( projectProjMgmts == null ) {
            return null;
        }

        List<ProjectProjMgmtResponse> list = new ArrayList<ProjectProjMgmtResponse>( projectProjMgmts.size() );
        for ( ProjectProjMgmt projectProjMgmt : projectProjMgmts ) {
            list.add( toResponse( projectProjMgmt ) );
        }

        return list;
    }
}
