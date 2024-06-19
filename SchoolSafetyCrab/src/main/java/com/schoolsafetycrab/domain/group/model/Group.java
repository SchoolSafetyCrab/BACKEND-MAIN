package com.schoolsafetycrab.domain.group.model;

import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Group", uniqueConstraints = {
        @UniqueConstraint(columnNames =
                {"school_name", "school_year","school_ban"}
        )
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Group extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long groupId;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "school_year", nullable = false)
    private int schoolYear;

    @Column(name = "school_ban", nullable = false)
    private int schoolBan;

    @Column(name = "user_num", nullable = false)
    private int userNum;

    @Column(name = "state", nullable = false)
    private boolean state;

    @Column(name = "group_code", nullable = false)
    private String groupCode;

    @Builder
    private Group(String schoolName, int schoolYear, int schoolBan, int userNum, boolean state, String groupCode) {
        this.schoolName = schoolName;
        this.schoolYear = schoolYear;
        this.schoolBan = schoolBan;
        this.userNum = userNum;
        this.state = state;
        this.groupCode = groupCode;
    }

    public static Group createGroup(CreateGroupRequestDto requestDto){
        return Group.builder()
                .schoolName(requestDto.getSchoolName())
                .schoolYear(requestDto.getSchoolYear())
                .schoolBan(requestDto.getSchoolBan())
                .userNum(requestDto.getUserNum())
                .state(true)
                .groupCode(requestDto.getGroupCode())
                .build();
    }

}
