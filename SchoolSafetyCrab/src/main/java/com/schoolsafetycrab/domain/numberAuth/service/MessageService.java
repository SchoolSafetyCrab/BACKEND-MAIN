package com.schoolsafetycrab.domain.numberAuth.service;

import com.schoolsafetycrab.domain.numberAuth.requestDto.SendAuthCodeRequestDto;
import com.schoolsafetycrab.domain.user.repository.UserRepository;
import com.schoolsafetycrab.global.config.MessageValueConfig;
import com.schoolsafetycrab.global.exception.CustomException;
import com.schoolsafetycrab.global.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageValueConfig messageValueConfig;
    private final UserRepository userRepository;
    private final NumberAuthService numberAuthService;

    public void sendAuthCode(SendAuthCodeRequestDto requestDto){
        if(userRepository.existsUserByPhoneNumber(requestDto.getPhoneNumber())){
            throw new ExceptionResponse(CustomException.DUPLICATED_NUMBER_EXCEPTION);
        }

        String authCode = numberAuthService.saveAuthCode(requestDto.getPhoneNumber());
        String text = createMessage(authCode);

        Message message = new Message();
        message.setFrom("01029463517");
        message.setTo(requestDto.getPhoneNumber());
        message.setText(text);

        // messageValueConfig.getDefaultMessageService().sendOne(new SingleMessageSendingRequest(message));
    }

    private String createMessage(String authCode){
        return "인증 번호는 " + authCode + " 입니다";
    }
}
