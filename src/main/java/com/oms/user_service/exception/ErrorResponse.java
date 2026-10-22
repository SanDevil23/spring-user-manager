
package com.oms.user_service.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
@Schema(description = "Standard API error response")
public class ErrorResponse {

    @Schema(
            description = "Timestamp when the error occurred",
            example = "2026-10-23T10:15:30Z"
    )
    private Instant timestamp;

    @Schema(
            description = "HTTP status code",
            example = "400"
    )
    private int status;

    @Schema(
            description = "Short description of the error category",
            example = "Validation Failed"
    )
    private String error;

    @Schema(
            description = "Human-readable explanation of the error",
            example = "Request validation failed"
    )
    private String message;

    @Schema(
            description = "API request path that produced the error",
            example = "/users/add"
    )
    private String path;

    @Schema(
            description = "Validation errors mapped by field name. Present when request validation fails.",
            example = "{\"username\":\"Username must be between 3 and 50 characters\"}"
    )
    private Map<String, String> fieldErrors;
}
