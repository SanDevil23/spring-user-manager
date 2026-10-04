package com.oms.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        // We're deliberately allowing either username or email for login.
        @NotBlank(message = "Username or email is required")
        String usernameOrEmail,

        @NotBlank(message = "Password is required")
        String password
) {}