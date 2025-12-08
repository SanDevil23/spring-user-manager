package com.oms.user_service.exception;

import com.oms.user_service.constants.ErrorCodeEnum;
import com.oms.user_service.pojo.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorResponse> handlerUserServiceException(UserServiceException ex){
        log.info("Global exception called to handle UserException: {}",ex.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(),
                ex.getErrorMessage());

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    public ResponseEntity<ErrorResponse> handleGenericErrors(Exception e){
        log.info("Handling Generic errors: {}",e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCodeEnum.GENERIC_ERROR.getErrorCode(),
                ErrorCodeEnum.GENERIC_ERROR.getErrorMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
