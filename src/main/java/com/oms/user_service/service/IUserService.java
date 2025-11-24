package com.oms.user_service.service;


import com.oms.user_service.model.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    User getUserById(Long userId);
    List<User> getAllUsers();
}
