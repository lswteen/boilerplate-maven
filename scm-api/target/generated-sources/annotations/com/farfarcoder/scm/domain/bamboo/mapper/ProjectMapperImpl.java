package com.farfarcoder.scm.domain.bamboo.mapper;

import com.farfarcoder.scm.domain.bamboo.entity.BuildEntity;
import com.farfarcoder.scm.domain.bamboo.entity.ProjectEntity;
import com.farfarcoder.scm.domain.bamboo.model.Build;
import com.farfarcoder.scm.domain.bamboo.model.Project;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-28T13:13:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toModel(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.projectId( projectEntity.getProjectId() );
        project.projectKey( projectEntity.getProjectKey() );
        project.title( projectEntity.getTitle() );
        project.description( projectEntity.getDescription() );
        project.builds( buildEntityCollectionToBuildCollection( projectEntity.getBuilds() ) );

        return project.build();
    }

    protected Build buildEntityToBuild(BuildEntity buildEntity) {
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
        build.projectEntity( toModel( buildEntity.getProjectEntity() ) );

        return build.build();
    }

    protected Collection<Build> buildEntityCollectionToBuildCollection(Collection<BuildEntity> collection) {
        if ( collection == null ) {
            return null;
        }

        Collection<Build> collection1 = new ArrayList<Build>( collection.size() );
        for ( BuildEntity buildEntity : collection ) {
            collection1.add( buildEntityToBuild( buildEntity ) );
        }

        return collection1;
    }
}
