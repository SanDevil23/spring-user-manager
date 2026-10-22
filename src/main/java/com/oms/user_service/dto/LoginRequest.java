
package com.oms.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credentials used to authenticate a user")
public record LoginRequest(

        @Schema(
                description = "Username or email address associated with the account",
                example = "john@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Username or email is required")
        String usernameOrEmail,

        @Schema(
                description = "Account password",
                example = "StrongPassword123",
                format = "password",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Password is required")
        String password
) {}
