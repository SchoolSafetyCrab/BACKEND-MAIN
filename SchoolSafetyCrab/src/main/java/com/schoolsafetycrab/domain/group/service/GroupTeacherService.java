package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.message.responseDto.GroupMemberResponseDto;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.repository.GroupRepository;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.group.requestDto.RegistGroupRequestDto;
import com.schoolsafetycrab.domain.schoolway.message.responseDto.PointResponseDto;
import com.schoolsafetycrab.domain.schoolwaypoint.model.SchoolWayPoint;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.usergroup.model.UserGroup;
import com.schoolsafetycrab.domain.usergroup.repository.UserGroupRepository;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import com.schoolsafetycrab.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GroupTeacherService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public void createGroup(Authentication authentication, CreateGroupRequestDto createGroupRequestDto) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        Group group = Group.createGroup(createGroupRequestDto);
        groupRepository.save(group);


        UserGroup userGroup = UserGroup.createUserGroup(group, user);
        userGroupRepository.save(userGroup);
    }


    public List<GroupMemberResponseDto> findGroupMembers(Authentication authentication, Long groupId) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        if(!userGroupRepository.existsByUser_UserIdAndGroup_GroupId(user.getUserId(), groupId))
            throw new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION);

        List<User> userList = userGroupRepository.findGroupMemberByGroupId(groupId);

        List<GroupMemberResponseDto> responses = new ArrayList<>();

        for(User member : userList) {
            responses.add(GroupMemberResponseDto.createGroupMemberResponseDto(member));
        }

        return responses;
    }

    public List<PointResponseDto> findGroupMemberSchoolWay(Authentication authentication, long groupId, long userId){
        User user = ((PrincipalDetails)authentication.getPrincipal()).getUser();

        if(!userGroupRepository.existsByUser_UserIdAndGroup_GroupId(user.getUserId(),groupId))
            throw new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION);

        if(!userGroupRepository.existsByUser_UserIdAndGroup_GroupId(userId,groupId))
            throw new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION);

        List<SchoolWayPoint> points = groupRepository.findSchoolWayByStudent(userId);
        List<PointResponseDto> responses = new ArrayList<>();

        for(SchoolWayPoint point : points){
            responses.add(PointResponseDto.createPointResponseDto(point));
        }

        return responses;
    }
}
