package com.schoolsafetycrab.domain.declarationImg.model;

import com.schoolsafetycrab.domain.declaration.model.Declaration;
import com.schoolsafetycrab.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeclarationImg extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "declaration_img_id")
    private long declarationImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "declaration_id")
    private Declaration declaration;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    private DeclarationImg(Declaration declaration, String imgUrl) {
        this.declaration = declaration;
        this.imgUrl = imgUrl;
    }

    public static DeclarationImg createDeclarationImg(Declaration declaration, String imgUrl){
        return new DeclarationImg(declaration,imgUrl);
    }
}
