package com.schoolsafetycrab.domain.guardian.model;

import com.schoolsafetycrab.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guardian_id")
    private long GuardianId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "id")
    private String id;
}