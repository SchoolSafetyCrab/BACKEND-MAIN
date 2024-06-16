package com.schoolsafetycrab.domain.declaration.model;

import com.schoolsafetycrab.domain.declaration.requestDto.DeclarationRequestDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "detail", columnDefinition = "TEXT", nullable = false)
    private String detail;

    @Builder
    private Declaration(User user, String latitude, String longitude, String title, String detail) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.detail = detail;
    }

    public static Declaration createDeclaration(User user, DeclarationRequestDto requestDto){
        return Declaration.builder()
                .user(user)
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .title(requestDto.getTitle())
                .detail(requestDto.getDetail())
                .build();
    }
}
