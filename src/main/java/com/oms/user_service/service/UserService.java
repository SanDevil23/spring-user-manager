package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepo;

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
        return userRepo.filterUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void DeleteUserById(Long userId){
        userRepo.deleteById(userId);
    }

    @Override
    public void DropUsers(){
        userRepo.deleteAll();
    }

    @Override
    public User UpdateUser(User user){
        return user;
    }
}
