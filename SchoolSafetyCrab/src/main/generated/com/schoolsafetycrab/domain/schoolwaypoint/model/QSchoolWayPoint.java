package com.schoolsafetycrab.domain.schoolwaypoint.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchoolWayPoint is a Querydsl query type for SchoolWayPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchoolWayPoint extends EntityPathBase<SchoolWayPoint> {

    private static final long serialVersionUID = -1535100268L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchoolWayPoint schoolWayPoint = new QSchoolWayPoint("schoolWayPoint");

    public final StringPath latitude = createString("latitude");

    public final StringPath longitude = createString("longitude");

    public final com.schoolsafetycrab.domain.schoolway.model.QSchoolWay schoolWay;

    public final NumberPath<Long> schoolWayPointId = createNumber("schoolWayPointId", Long.class);

    public QSchoolWayPoint(String variable) {
        this(SchoolWayPoint.class, forVariable(variable), INITS);
    }

    public QSchoolWayPoint(Path<? extends SchoolWayPoint> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchoolWayPoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchoolWayPoint(PathMetadata metadata, PathInits inits) {
        this(SchoolWayPoint.class, metadata, inits);
    }

    public QSchoolWayPoint(Class<? extends SchoolWayPoint> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schoolWay = inits.isInitialized("schoolWay") ? new com.schoolsafetycrab.domain.schoolway.model.QSchoolWay(forProperty("schoolWay"), inits.get("schoolWay")) : null;
    }

}

