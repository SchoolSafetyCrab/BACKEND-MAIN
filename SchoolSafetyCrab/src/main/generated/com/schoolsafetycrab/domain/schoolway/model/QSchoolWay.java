package com.schoolsafetycrab.domain.schoolway.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchoolWay is a Querydsl query type for SchoolWay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchoolWay extends EntityPathBase<SchoolWay> {

    private static final long serialVersionUID = -1583370428L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchoolWay schoolWay = new QSchoolWay("schoolWay");

    public final com.schoolsafetycrab.global.baseTimeEntity.QBaseTimeEntity _super = new com.schoolsafetycrab.global.baseTimeEntity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> schoolWayId = createNumber("schoolWayId", Long.class);

    public final com.schoolsafetycrab.domain.user.model.QUser user;

    public QSchoolWay(String variable) {
        this(SchoolWay.class, forVariable(variable), INITS);
    }

    public QSchoolWay(Path<? extends SchoolWay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchoolWay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchoolWay(PathMetadata metadata, PathInits inits) {
        this(SchoolWay.class, metadata, inits);
    }

    public QSchoolWay(Class<? extends SchoolWay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.schoolsafetycrab.domain.user.model.QUser(forProperty("user")) : null;
    }

}

