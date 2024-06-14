package com.schoolsafetycrab.domain.declaration.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeclaration is a Querydsl query type for Declaration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeclaration extends EntityPathBase<Declaration> {

    private static final long serialVersionUID = -1256548318L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeclaration declaration = new QDeclaration("declaration");

    public final com.schoolsafetycrab.global.baseTimeEntity.QBaseTimeEntity _super = new com.schoolsafetycrab.global.baseTimeEntity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> declarationId = createNumber("declarationId", Long.class);

    public final StringPath detail = createString("detail");

    public final StringPath latitude = createString("latitude");

    public final StringPath longitude = createString("longitude");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    public final com.schoolsafetycrab.domain.user.model.QUser user;

    public QDeclaration(String variable) {
        this(Declaration.class, forVariable(variable), INITS);
    }

    public QDeclaration(Path<? extends Declaration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeclaration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeclaration(PathMetadata metadata, PathInits inits) {
        this(Declaration.class, metadata, inits);
    }

    public QDeclaration(Class<? extends Declaration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.schoolsafetycrab.domain.user.model.QUser(forProperty("user")) : null;
    }

}

