package com.oms.user_service.dto;

import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponseDto {

    private long userId;
    private String userName;
    private String email;
    private Status status;
    private boolean admin;
    private Date createdAt;
    private Date updatedAt;

}