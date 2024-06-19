package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.model.Group;
import com.schoolsafetycrab.domain.group.repository.GroupRepository;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.user.model.Role;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public void createGroup(Authentication authentication, CreateGroupRequestDto requestDto) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        if(!user.getRole().equals(Role.ROLE_TEACHER)){
            throw new ExceptionResponse(CustomException.ACCESS_DENIEND_EXCEPTION);
        }

        Group group = Group.createGroup(requestDto);
        groupRepository.save(group);


        UserGroup userGroup = UserGroup.createUserGroup(group, user);
        userGroupRepository.save(userGroup);
    }
}
