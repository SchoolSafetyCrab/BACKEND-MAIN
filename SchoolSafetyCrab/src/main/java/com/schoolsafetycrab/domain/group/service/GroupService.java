package com.schoolsafetycrab.domain.group.service;

import com.schoolsafetycrab.domain.group.model.Grouping;
import com.schoolsafetycrab.domain.group.repository.GroupingRepository;
import com.schoolsafetycrab.domain.group.requestDto.CreateGroupRequestDto;
import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.usergroup.model.UserGrouping;
import com.schoolsafetycrab.domain.usergroup.repository.UserGroupingRepository;
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

    private final GroupingRepository groupingRepository;
    private final UserGroupingRepository userGroupingRepository;

    @Transactional
    public void createGroup(Authentication authentication, CreateGroupRequestDto createGroupRequestDto) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        Grouping grouping = Grouping.createGroup(createGroupRequestDto);
        groupingRepository.save(grouping);


        UserGrouping userGrouping = UserGrouping.createUserGroup(grouping, user);
        userGroupingRepository.save(userGrouping);
    }
}
