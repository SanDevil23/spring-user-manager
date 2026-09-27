package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.mapper.UserMapper;
import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(CreateUserRequest req){
        try {
            User user = userMapper.toEntity(req);
            return userMapper.toDto(userRepo.save(user));
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        return userMapper.toDto(userRepo.filterUserById(userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserResponseDto deleteUserById(Long userId){
        if (userRepo.existsById(userId)){
            User user = userRepo.filterUserById(userId);
            userRepo.deleteById(userId);
            return userMapper.toDto(user);
        }
        // log if the user does not exist
        return null;
    }

    @Override
    public void dropUsers(){
        userRepo.deleteAll();
    }

    /**
     * Method to update majority fields in the existing user
     *
     * @param updatedUser Updated user object passed down from the API layer
     * @return returns the updated user state
     */
    @Override
    public UserResponseDto updateUser(User updatedUser){
        // extract user id from the request
        long id = updatedUser.getId();

        // fetch the user by id
        User userToBeUpdated = userRepo.filterUserById(id);
        return userMapper.toDto(userToBeUpdated);

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
