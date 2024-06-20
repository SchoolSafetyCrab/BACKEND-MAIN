package com.schoolsafetycrab.domain.usergroup.model;

import com.schoolsafetycrab.domain.group.model.Grouping;
import com.schoolsafetycrab.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserGrouping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_grouping_id")
    private long userGroupingId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Grouping grouping;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private UserGrouping(Grouping grouping, User user) {
        this.grouping = grouping;
        this.user = user;
    }

    public static UserGrouping createUserGroup(Grouping grouping, User user) {
        return new UserGrouping(grouping, user);
    }
}
