package com.oms.user_service.dto;

import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

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
                .UserName(dto.getUserName())
                .Email(dto.getEmail())
                .isAdmin(dto.isAdmin())
                .status(Status.DISABLED)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }
}