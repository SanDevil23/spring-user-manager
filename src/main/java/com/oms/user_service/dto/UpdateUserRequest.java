package com.oms.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UpdateUserRequest {
    @NotBlank
    long userId;
    String status;
}
