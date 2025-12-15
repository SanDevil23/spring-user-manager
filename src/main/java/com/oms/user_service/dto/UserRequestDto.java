package com.oms.user_service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequestDto {

    private String UserName;
    private Date DOB;

}
