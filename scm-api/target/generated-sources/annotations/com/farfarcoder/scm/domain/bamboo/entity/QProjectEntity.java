package com.farfarcoder.scm.domain.bamboo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectEntity is a Querydsl query type for ProjectEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectEntity extends EntityPathBase<ProjectEntity> {

    private static final long serialVersionUID = -332410488L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectEntity projectEntity = new QProjectEntity("projectEntity");

    public final CollectionPath<BuildEntity, QBuildEntity> builds = this.<BuildEntity, QBuildEntity>createCollection("builds", BuildEntity.class, QBuildEntity.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final StringPath projectKey = createString("projectKey");

    public final QProjMgmtEntity projMgmt;

    public final StringPath title = createString("title");

    public QProjectEntity(String variable) {
        this(ProjectEntity.class, forVariable(variable), INITS);
    }

    public QProjectEntity(Path<? extends ProjectEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectEntity(PathMetadata metadata, PathInits inits) {
        this(ProjectEntity.class, metadata, inits);
    }

    public QProjectEntity(Class<? extends ProjectEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.projMgmt = inits.isInitialized("projMgmt") ? new QProjMgmtEntity(forProperty("projMgmt"), inits.get("projMgmt")) : null;
    }

}

