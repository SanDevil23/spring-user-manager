package com.oms.user_service.service;


import com.oms.user_service.dto.CreateUserRequestDto;
import com.oms.user_service.model.User;

import java.util.List;

public interface IUserService {
    User createUser(CreateUserRequestDto req);
    User getUserById(Long userId);
    List<User> getAllUsers();
    User deleteUserById(Long userId);
    void dropUsers();
    User updateUser(User user);
    void updateUserStatus(Long id, String state);
}
