package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.dto.CreateUserRequestDto;
import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepo;

    @Override
    public User createUser(CreateUserRequestDto req){
        try {
            User user = req.toEntity(req);
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
        // log if the user does not exist
        return null;
    }

    @Override
    public void DropUsers(){
        userRepo.deleteAll();
    }

    /**
     * Method to update majority fields in the existing user
     * @param updatedUser Updated user object passed down from the API layer
     * @return returns the updated user state
     */
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

    /**
     * Method to update the user status
     * @param userid
     * @param state
     */
    @Override
    public void updateUserStatus(Long userid, String state){
        User userToUpdate = userRepo.filterUserById(userid);
        state = state.toLowerCase().trim();
        switch(state) {
            case "active":
                userToUpdate.setStatus(Status.ACTIVE);
                break;
            case "disabled":
                userToUpdate.setStatus(Status.DISABLED);
                break;
            case "locked":
                userToUpdate.setStatus(Status.LOCKED);
                break;
        }
        return;
    }
}
