package com.farfarcoder.scm.web.dashboard.mapper;

import com.farfarcoder.scm.domain.bamboo.model.Build;
import com.farfarcoder.scm.domain.bamboo.model.ProjectBuild;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjectBuildResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T17:15:27+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class ProjectBuildWebMapperImpl implements ProjectBuildWebMapper {

    @Override
    public ProjectBuildResponse toResponse(ProjectBuild projectBuild) {
        if ( projectBuild == null ) {
            return null;
        }

        Long projectId = null;
        String projectKey = null;
        String title = null;
        String description = null;
        List<ProjectBuildResponse.BuildResponse> builds = null;

        projectId = projectBuild.projectId();
        projectKey = projectBuild.projectKey();
        title = projectBuild.title();
        description = projectBuild.description();
        builds = buildListToBuildResponseList( projectBuild.builds() );

        ProjectBuildResponse projectBuildResponse = new ProjectBuildResponse( projectId, projectKey, title, description, builds );

        return projectBuildResponse;
    }

    @Override
    public List<ProjectBuildResponse> toResponseList(List<ProjectBuild> projectBuilds) {
        if ( projectBuilds == null ) {
            return null;
        }

        List<ProjectBuildResponse> list = new ArrayList<ProjectBuildResponse>( projectBuilds.size() );
        for ( ProjectBuild projectBuild : projectBuilds ) {
            list.add( toResponse( projectBuild ) );
        }

        return list;
    }

    @Override
    public ProjectBuildResponse.BuildResponse toBuildResponse(Build build) {
        if ( build == null ) {
            return null;
        }

        ProjectBuildResponse.BuildResponse.BuildResponseBuilder buildResponse = ProjectBuildResponse.BuildResponse.builder();

        buildResponse.buildId( build.buildId() );
        buildResponse.projectId( build.projectId() );
        buildResponse.buildTitle( build.buildTitle() );
        buildResponse.buildType( build.buildType() );
        buildResponse.createdAt( build.createdAt() );
        buildResponse.updatedAt( build.updatedAt() );

        return buildResponse.build();
    }

    protected List<ProjectBuildResponse.BuildResponse> buildListToBuildResponseList(List<Build> list) {
        if ( list == null ) {
            return null;
        }

        List<ProjectBuildResponse.BuildResponse> list1 = new ArrayList<ProjectBuildResponse.BuildResponse>( list.size() );
        for ( Build build : list ) {
            list1.add( toBuildResponse( build ) );
        }

        return list1;
    }
}
