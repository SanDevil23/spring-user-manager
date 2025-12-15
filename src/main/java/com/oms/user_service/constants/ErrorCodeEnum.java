package com.oms.user_service.constants;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    INVALID_USERNAME("1000","Invalid Username"),
    INVALID_DOB("1001","Invalid Date Of Birth"),
    INVALID_REQUEST("1002","Invalid Request"),
    USER_ALREADY_EXIST("1003","Username Already Taken"),
    USER_NOT_FOUND("1004","User Not Found"),
    GENERIC_ERROR("1005","Oopss Something went wrong...");


    private final String errorCode;
    private final String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode=errorCode;
        this.errorMessage = errorMessage;
    }
}
