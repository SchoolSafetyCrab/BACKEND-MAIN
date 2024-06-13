package com.schoolsafetycrab.domain.declaration.model;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Declaration extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "declaration_id")
    private long declarationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    @Builder
    private Declaration(User user, String latitude, String longitude, String title, String detail) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.detail = detail;
    }

    public static Declaration createDeclaration(){

    }
}
