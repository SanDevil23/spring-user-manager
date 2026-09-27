package com.oms.user_service.dto;

import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;

@Data
public class CreateUserRequestDto {

    @NotBlank
    private String userName;

    @Email
    @NotBlank
    private String email;
    private boolean admin;


    public User toEntity(CreateUserRequestDto dto) {
        return User.builder()
                .username(dto.getUserName())
                .email(dto.getEmail())
                .admin(dto.isAdmin())
                .status(Status.DISABLED)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}