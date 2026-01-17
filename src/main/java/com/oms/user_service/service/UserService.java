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
    public User DeleteUserById(Long userId){
        if (userRepo.existsById(userId)){
            User user = userRepo.filterUserById(userId);
            userRepo.deleteById(userId);
            return user;
        }
        // log if the user does not exists
        return null;
    }

    @Override
    public void DropUsers(){
        userRepo.deleteAll();
    }

    @Override
    public User UpdateUser(User updatedUser){
        // extract user id from the request
        long id = updatedUser.getUserId();

        // fetch the user by id
        User userToBeUpdated = userRepo.filterUserById(id);

        // replace the existing user object with the updated one
        userToBeUpdated = updatedUser;

        return userToBeUpdated;

    }
}
