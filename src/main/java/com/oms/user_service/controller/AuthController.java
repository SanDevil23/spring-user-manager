package com.oms.user_service.controller;

import com.oms.user_service.dto.AuthResponse;
import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.LoginRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req){
        AuthResponse res = authService.login(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody CreateUserRequest req
    ){
        UserResponseDto res = authService.register(req);

        return ResponseEntity.ok(res);
    }
}
