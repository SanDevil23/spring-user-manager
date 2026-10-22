
package com.oms.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating a user account")
public record CreateUserRequest(

        @Schema(
                description = "Unique username",
                example = "john_doe",
                minLength = 3,
                maxLength = 50,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "username is required")
        @Size(min = 3, max = 50,
                message = "Username must be between 3 and 50 characters")
        String username,

        @Schema(
                description = "User's email address",
                example = "john@example.com",
                maxLength = 255,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255,
                message = "Email must not exceed 255 characters")
        String email,

        @Schema(
                description = "Account password; never returned in API responses",
                example = "StrongPassword123",
                minLength = 8,
                maxLength = 100,
                requiredMode = Schema.RequiredMode.REQUIRED,
                format = "password"
        )
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100,
                message = "Password must be between 8 and 100 characters")
        String password
) {
}
