
package com.oms.user_service.dto;

import com.oms.user_service.util.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@Schema(description = "User account details returned by the User Manager API")
public class UserResponseDto {

    @Schema(description = "Unique user identifier", example = "101")
    private Long id;

    @Schema(description = "Username", example = "john_doe")
    private String userName;

    @Schema(description = "User email address", example = "john@example.com")
    private String email;

    @Schema(
            description = "Current account status",
            example = "ACTIVE"
    )
    private Status status;

    @Schema(description = "Whether the account has administrator privileges", example = "false")
    private boolean admin;

    @Schema(
            description = "Timestamp when the account was created",
            example = "2026-10-20T10:15:30Z",
            format = "date-time"
    )
    private Instant createdAt;

    @Schema(
            description = "Timestamp when the account was last updated",
            example = "2026-10-21T12:30:00Z",
            format = "date-time"
    )
    private Instant updatedAt;
}
