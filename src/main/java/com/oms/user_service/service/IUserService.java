package com.oms.user_service.service;


import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.UpdateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.model.User;

import java.util.List;

public interface IUserService {
    UserResponseDto createUser(CreateUserRequest req);
    UserResponseDto getUserById(Long userId);
    List<User> getAllUsers();
    UserResponseDto deleteUserById(Long userId);
    void dropUsers();
    UserResponseDto updateUser(Long id, UpdateUserRequest user);
    void updateUserStatus(Long id, String state);
}
