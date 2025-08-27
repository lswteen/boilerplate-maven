package com.farfarcoder.scm.domain.bamboo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBuildEntity is a Querydsl query type for BuildEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuildEntity extends EntityPathBase<BuildEntity> {

    private static final long serialVersionUID = -1234824163L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBuildEntity buildEntity = new QBuildEntity("buildEntity");

    public final NumberPath<Long> buildId = createNumber("buildId", Long.class);

    public final StringPath buildTitle = createString("buildTitle");

    public final StringPath buildType = createString("buildType");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final QProjectEntity projectEntity;

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final DateTimePath<java.time.Instant> updatedAt = createDateTime("updatedAt", java.time.Instant.class);

    public QBuildEntity(String variable) {
        this(BuildEntity.class, forVariable(variable), INITS);
    }

    public QBuildEntity(Path<? extends BuildEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBuildEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBuildEntity(PathMetadata metadata, PathInits inits) {
        this(BuildEntity.class, metadata, inits);
    }

    public QBuildEntity(Class<? extends BuildEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.projectEntity = inits.isInitialized("projectEntity") ? new QProjectEntity(forProperty("projectEntity"), inits.get("projectEntity")) : null;
    }

}

