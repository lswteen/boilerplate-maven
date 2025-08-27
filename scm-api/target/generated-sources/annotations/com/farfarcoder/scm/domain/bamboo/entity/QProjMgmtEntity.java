package com.farfarcoder.scm.domain.bamboo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjMgmtEntity is a Querydsl query type for ProjMgmtEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjMgmtEntity extends EntityPathBase<ProjMgmtEntity> {

    private static final long serialVersionUID = -1664106603L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjMgmtEntity projMgmtEntity = new QProjMgmtEntity("projMgmtEntity");

    public final StringPath bambooDesc = createString("bambooDesc");

    public final StringPath bambooKey = createString("bambooKey");

    public final StringPath bambooName = createString("bambooName");

    public final StringPath bitbucketDesc = createString("bitbucketDesc");

    public final StringPath bitbucketKey = createString("bitbucketKey");

    public final StringPath bitbucketName = createString("bitbucketName");

    public final StringPath bizDiv = createString("bizDiv");

    public final StringPath bizMgr = createString("bizMgr");

    public final StringPath config = createString("config");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final StringPath deployMgr = createString("deployMgr");

    public final StringPath dev = createString("dev");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath oper = createString("oper");

    public final QProjectEntity projectEntity;

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final StringPath status = createString("status");

    public final DateTimePath<java.time.Instant> updatedAt = createDateTime("updatedAt", java.time.Instant.class);

    public QProjMgmtEntity(String variable) {
        this(ProjMgmtEntity.class, forVariable(variable), INITS);
    }

    public QProjMgmtEntity(Path<? extends ProjMgmtEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjMgmtEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjMgmtEntity(PathMetadata metadata, PathInits inits) {
        this(ProjMgmtEntity.class, metadata, inits);
    }

    public QProjMgmtEntity(Class<? extends ProjMgmtEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.projectEntity = inits.isInitialized("projectEntity") ? new QProjectEntity(forProperty("projectEntity"), inits.get("projectEntity")) : null;
    }

}

