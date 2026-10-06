package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.dto.AuthResponse;
import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.LoginRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.exception.ResourceNotFoundException;
import com.oms.user_service.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            IUserService userService,
            JwtService jwtService
    ){
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    public UserResponseDto register(CreateUserRequest req){
        return userService.createAdminUser(req);
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

        String accessToken = jwtService.generateToken(user.getId());

        return new AuthResponse(accessToken);
    }
}
