package com.oms.user_service.dto;

import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;

@Getter
@Builder
public class UserResponseDto {

    private Long id;
    private String userName;
    private String email;
    private Status status;
    private boolean admin;
    private Instant createdAt;
    private Instant updatedAt;

}