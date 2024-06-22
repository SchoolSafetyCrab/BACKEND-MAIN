package com.schoolsafetycrab.domain.group.message.responseDto;

import com.schoolsafetycrab.domain.group.model.Group;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupInfoResponseDto {
    private long groupId;

    private String schoolName;

    private int schoolYear;

    private int schoolBan;

    private boolean state;

    public GroupInfoResponseDto(long groupId, String schoolName, int schoolYear, int schoolBan, boolean state) {
        this.groupId = groupId;
        this.schoolName = schoolName;
        this.schoolYear = schoolYear;
        this.schoolBan = schoolBan;
        this.state = state;
    }

    public static GroupInfoResponseDto createGroupInfoResponseDto(Group group) {
        return new GroupInfoResponseDto(group.getGroupId(), group.getSchoolName(), group.getSchoolYear(), group.getSchoolBan(), group.isState());
    }
}
