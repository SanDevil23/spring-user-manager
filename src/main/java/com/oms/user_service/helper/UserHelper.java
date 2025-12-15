package com.oms.user_service.helper;

import com.oms.user_service.constants.ErrorCodeEnum;
import com.oms.user_service.dto.UserRequestDto;
import com.oms.user_service.exception.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserHelper {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]+$";
    private static final int MIN_USERNAME = 3;

    public void validateUserRequest(UserRequestDto userRequest) {
        Date currentDate = new Date();

        if(userRequest == null){
            throw new UserServiceException(
                    ErrorCodeEnum.INVALID_REQUEST.getErrorCode(),
                    ErrorCodeEnum.INVALID_REQUEST.getErrorMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }

        String username = userRequest.getUserName().strip();

        if (username == null || username.isBlank() ||
                username.length() > MIN_USERNAME || username.matches(USERNAME_PATTERN)){
            throw new UserServiceException(
                    ErrorCodeEnum.INVALID_USERNAME.getErrorCode(),
                    ErrorCodeEnum.INVALID_USERNAME.getErrorMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (userRequest.getDOB() == null){
            throw new UserServiceException(
                    ErrorCodeEnum.INVALID_DOB.getErrorCode(),
                    ErrorCodeEnum.INVALID_DOB.getErrorMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }

        Date date = userRequest.getDOB();

        if (date.before(currentDate)){
            throw new UserServiceException(
                    ErrorCodeEnum.INVALID_DOB.getErrorCode(),
                    ErrorCodeEnum.INVALID_DOB.getErrorMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }






    }
}
