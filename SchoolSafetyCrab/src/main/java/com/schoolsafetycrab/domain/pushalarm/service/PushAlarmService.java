package com.schoolsafetycrab.domain.pushalarm.service;

import com.schoolsafetycrab.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PushAlarmService {

    private final SubjectService subjectService;

    public void pushAlarm(long groupId, long notificationID){
        List<User> students = subjectService.getGroupStudent(groupId);

        subjectService.notifyUser(students, notificationID);
    }

}
