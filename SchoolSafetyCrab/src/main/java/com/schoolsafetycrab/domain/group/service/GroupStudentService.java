package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.message.responseDto.GroupInfoResponseDto;
import com.schoolsafetycrab.domain.group.message.responseDto.GroupMemberResponseDto;
import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.repository.GroupRepository;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.group.requestDto.RegistGroupRequestDto;
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
public class GroupStudentService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public void registGroup(Authentication authentication, RegistGroupRequestDto registGroupRequestDto) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        if(userGroupRepository.existsByUser_UserIdAndGroup_GroupId(user.getUserId(), registGroupRequestDto.getGroupId()))
            throw new ExceptionResponse(CustomException.DUPLICATED_GROUP_MEMBER_EXCEPTION);


        Optional<Group> foundedGroup = groupRepository.findByGroupIdAndGroupCode(registGroupRequestDto.getGroupId(),
                registGroupRequestDto.getGroupCode());

        Group group = foundedGroup
                .orElseThrow(() -> new ExceptionResponse(CustomException.NOT_MATCH_GROUP_CODE_EXCEPTION));


        long currentUserNum = userGroupRepository.countByGroup_GroupId(group.getGroupId());

        if (currentUserNum == group.getUserNum()) {
            throw new ExceptionResponse(CustomException.EXCEED_GROUP_MEMBER_LIMIT_EXCEPTION);
        }


        UserGroup userGroup = UserGroup.createUserGroup(group, user);
        userGroupRepository.save(userGroup);
    }

    public List<GroupInfoResponseDto> searchGroup(String keyword){
        List<Group> groupList = groupRepository.findGroupByKeyword(keyword);

        List<GroupInfoResponseDto> responses = new ArrayList<>();

        for(Group group : groupList) {
            responses.add(GroupInfoResponseDto.createGroupInfoResponseDto(group));
        }

        return responses;
    }
}