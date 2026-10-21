
package com.oms.user_service.controller;

import com.oms.user_service.dto.AuthResponse;
import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.LoginRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Authentication",
        description = "Endpoints for user login and registration"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Log in",
            description = "Authenticates a user using their username or email and password. " +
                    "Returns a JWT access token for subsequent authenticated requests.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "User login credentials",
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful",
                            content = @Content(
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials or inactive account"
                    )
            }
    )
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest req
    ) {
        AuthResponse res = authService.login(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register an administrator",
            description = "Creates an administrator account using the supplied registration details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Account registration details",
                    content = @Content(
                            schema = @Schema(implementation = CreateUserRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registration successful",
                            content = @Content(
                                    schema = @Schema(implementation = UserResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Username or email already exists"
                    )
            }
    )
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody CreateUserRequest req
    ) {
        UserResponseDto res = authService.register(req);
        return ResponseEntity.ok(res);
    }
}
