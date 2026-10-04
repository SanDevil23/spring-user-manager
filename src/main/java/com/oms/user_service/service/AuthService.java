package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.dto.AuthResponse;
import com.oms.user_service.dto.LoginRequest;
import com.oms.user_service.exception.ResourceNotFoundException;
import com.oms.user_service.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepo,
            PasswordEncoder passwordEncoder
    ){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByUsernameOrEmail(
                req.usernameOrEmail(),
                req.usernameOrEmail()
        ).orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

        boolean passwordMatches = passwordEncoder.matches(req.password(), user.getPasswordHash());

        if (!passwordMatches){
            throw new ResourceNotFoundException("Invalid Credentials");
        }

        return new AuthResponse("TEMPORARY_TOKEN");
    }
}
