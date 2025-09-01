package com.farfarcoder.scm.domain.bamboo.mapper;

import com.farfarcoder.scm.domain.bamboo.entity.BuildEntity;
import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.model.Build;
import com.farfarcoder.scm.domain.bamboo.model.ProjectBuild;
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
public class ProjectBuildMapperImpl implements ProjectBuildMapper {

    @Override
    public ProjectBuild toModel(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }

        ProjectBuild.ProjectBuildBuilder projectBuild = ProjectBuild.builder();

        projectBuild.projectId( projectEntity.getProjectId() );
        projectBuild.projectKey( projectEntity.getProjectKey() );
        projectBuild.title( projectEntity.getTitle() );
        projectBuild.description( projectEntity.getDescription() );
        projectBuild.builds( mapBuilds( projectEntity.getBuilds() ) );

        return projectBuild.build();
    }

    @Override
    public List<ProjectBuild> toModelList(List<ProjectEntity> projectEntities) {
        if ( projectEntities == null ) {
            return null;
        }

        List<ProjectBuild> list = new ArrayList<ProjectBuild>( projectEntities.size() );
        for ( ProjectEntity projectEntity : projectEntities ) {
            list.add( toModel( projectEntity ) );
        }

        return list;
    }

    @Override
    public Build buildEntityToBuild(BuildEntity buildEntity) {
        if ( buildEntity == null ) {
            return null;
        }

        Build.BuildBuilder build = Build.builder();

        build.buildId( buildEntity.getBuildId() );
        build.projectId( buildEntity.getProjectId() );
        build.buildTitle( buildEntity.getBuildTitle() );
        build.buildType( buildEntity.getBuildType() );
        build.createdAt( buildEntity.getCreatedAt() );
        build.updatedAt( buildEntity.getUpdatedAt() );

        return build.build();
    }
}
