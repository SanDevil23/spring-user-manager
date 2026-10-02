package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.UpdateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.exception.DuplicateResourceException;
import com.oms.user_service.exception.ResourceNotFoundException;
import com.oms.user_service.mapper.UserMapper;
import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    /**
     * Create a new user if it doesn't already exist in the system
     * @param req Request body passed down from the API layer to the Service layer
     * @return
     */
    @Override
    public UserResponseDto createUser(CreateUserRequest req){
        if (userRepo.existsByEmail(req.email())){
            throw new DuplicateResourceException(
                    "Email already exists: " + req.email()
            );
        }
        if (userRepo.existsByUsername(req.username())){
            throw new DuplicateResourceException(
                    "Username already exists: " + req.username()
            );
        }
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
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable).map(userMapper::toDto);
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

        if (!userToBeUpdated.getUsername().equals(req.getUsername()) && userRepo.existsByUsername(req.getUsername())){
            throw new DuplicateResourceException(
                    "Username already exists: " + req.getUsername()
            );
        }

        if (!userToBeUpdated.getEmail().equals(req.getEmail()) && userRepo.existsByEmail(req.getEmail())){
            throw new DuplicateResourceException(
                    "Email already exists: " + req.getEmail()
            );
        }

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
