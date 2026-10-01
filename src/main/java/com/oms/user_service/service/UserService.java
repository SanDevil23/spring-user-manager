package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.UpdateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.exception.ResourceNotFoundException;
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
        User user = userMapper.toEntity(req);
        return userMapper.toDto(userRepo.save(user));
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User fetchUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id: " + userId));;
        return userMapper.toDto(fetchUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDto deleteUserById(Long userId){
        User userToDelete = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id: " + userId));;

        userRepo.delete(userToDelete);
        return userMapper.toDto(userToDelete);
    }

    @Override
    public void dropUsers(){
        userRepo.deleteAll();
    }

    /**
     * Method to update majority fields in the existing user
     * @param req Updated user object passed down from the API layer
     * @return returns the updated user state
     */
    @Override
    public UserResponseDto updateUser(Long id, UpdateUserRequest req){
        User userToBeUpdated = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id: " + id));

        userToBeUpdated.setUsername(req.getUsername());
        userToBeUpdated.setEmail(req.getEmail());

        User updatedUser = userRepo.save(userToBeUpdated);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Method to update the user status
     * @param userid
     * @param state
     */
    @Override
    public void updateUserStatus(Long userid, String state){
        User userToUpdate = userRepo.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id: " + userid));;
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
    }
}
