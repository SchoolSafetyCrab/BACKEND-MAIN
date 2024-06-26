package com.schoolsafetycrab.domain.userdevice.service;

import com.schoolsafetycrab.domain.user.model.User;
import com.schoolsafetycrab.domain.userdevice.model.UserDevice;
import com.schoolsafetycrab.domain.userdevice.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    public void saveUserDeviceToken(User user, String deviceToken){
        UserDevice userDevice = UserDevice.createUserDevice(user, deviceToken);
        if(userDeviceRepository.existsByUser_UserId(user.getUserId()))
            userDeviceRepository.updateDeviceToken(userDevice);
        else
            userDeviceRepository.save(userDevice);
    }

}
