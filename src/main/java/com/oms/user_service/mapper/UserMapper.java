package com.oms.user_service.mapper;

import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest req) {
        return User.builder()
            .username(req.username())
            .email(req.email())
            .status(Status.LOCKED)
            .admin(false)
            .build();
    }

    public UserResponseDto toDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .admin(user.isAdmin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt()).build();
    }
}
