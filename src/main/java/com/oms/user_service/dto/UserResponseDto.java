package com.oms.user_service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDto {

    private long UserId;
    private String UserName;
    private Date DOB;
    private boolean isAdmin;
    private Date createdAt;
    private Date updatedAt;
}
