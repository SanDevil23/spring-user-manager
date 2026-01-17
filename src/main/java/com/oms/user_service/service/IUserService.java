package com.oms.user_service.service;


import com.oms.user_service.dto.CreateUserRequestDto;
import com.oms.user_service.model.User;

import java.util.List;

public interface IUserService {
    User createUser(CreateUserRequestDto req);
    User getUserById(Long userId);
    List<User> getAllUsers();
    User DeleteUserById(Long userId);
    void DropUsers();
    User UpdateUser(User user);
    void updateUserStatus(Long id, String state);
}
