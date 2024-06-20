package com.schoolsafetycrab.domain.group.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroup is a Querydsl query type for Group
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = -10855060L;

    public static final QGroup group = new QGroup("group1");

    public final com.schoolsafetycrab.global.baseTimeEntity.QBaseTimeEntity _super = new com.schoolsafetycrab.global.baseTimeEntity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath groupCode = createString("groupCode");

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Integer> schoolBan = createNumber("schoolBan", Integer.class);

    public final StringPath schoolName = createString("schoolName");

    public final NumberPath<Integer> schoolYear = createNumber("schoolYear", Integer.class);

    public final BooleanPath state = createBoolean("state");

    public final NumberPath<Integer> userNum = createNumber("userNum", Integer.class);

    public QGroup(String variable) {
        super(Group.class, forVariable(variable));
    }

    public QGroup(Path<? extends Group> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroup(PathMetadata metadata) {
        super(Group.class, metadata);
    }

}

