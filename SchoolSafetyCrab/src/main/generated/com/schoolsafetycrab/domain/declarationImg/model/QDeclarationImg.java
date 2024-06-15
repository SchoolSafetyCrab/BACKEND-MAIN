package com.schoolsafetycrab.domain.declarationImg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeclarationImg is a Querydsl query type for DeclarationImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeclarationImg extends EntityPathBase<DeclarationImg> {

    private static final long serialVersionUID = 1035163412L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeclarationImg declarationImg = new QDeclarationImg("declarationImg");

    public final com.schoolsafetycrab.global.baseTimeEntity.QBaseTimeEntity _super = new com.schoolsafetycrab.global.baseTimeEntity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final com.schoolsafetycrab.domain.declaration.model.QDeclaration declaration;

    public final NumberPath<Long> declarationImgId = createNumber("declarationImgId", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QDeclarationImg(String variable) {
        this(DeclarationImg.class, forVariable(variable), INITS);
    }

    public QDeclarationImg(Path<? extends DeclarationImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeclarationImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeclarationImg(PathMetadata metadata, PathInits inits) {
        this(DeclarationImg.class, metadata, inits);
    }

    public QDeclarationImg(Class<? extends DeclarationImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.declaration = inits.isInitialized("declaration") ? new com.schoolsafetycrab.domain.declaration.model.QDeclaration(forProperty("declaration"), inits.get("declaration")) : null;
    }

}

