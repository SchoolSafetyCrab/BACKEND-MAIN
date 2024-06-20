package com.schoolsafetycrab.domain.group.model;

import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Grouping extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grouping_id")
    private long groupingId;

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
    private Grouping(String schoolName, int schoolYear, int schoolBan, int userNum, boolean state, String groupCode) {
        this.schoolName = schoolName;
        this.schoolYear = schoolYear;
        this.schoolBan = schoolBan;
        this.userNum = userNum;
        this.state = state;
        this.groupCode = groupCode;
    }

    public static Grouping createGroup(CreateGroupRequestDto requestDto){
        return Grouping.builder()
                .schoolName(requestDto.getSchoolName().replaceAll("\\s+", ""))
                .schoolYear(requestDto.getSchoolYear())
                .schoolBan(requestDto.getSchoolBan())
                .userNum(requestDto.getUserNum())
                .state(true)
                .groupCode(requestDto.getGroupCode())
                .build();
    }

}
