package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user){
        try {
            return userRepo.save(user);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}
