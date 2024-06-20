package com.schoolsafetycrab.domain.usergroup.model;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_group_id")
    private long userGroupId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private UserGroup(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    public static UserGroup createUserGroup(Group group, User user) {
        return new UserGroup(group, user);
    }
}
