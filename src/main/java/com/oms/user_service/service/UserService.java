package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.UpdateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.exception.DuplicateResourceException;
import com.oms.user_service.exception.ResourceNotFoundException;
import com.oms.user_service.mapper.UserMapper;
import com.oms.user_service.model.User;
import com.oms.user_service.specification.UserSpecification;
import com.oms.user_service.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create a fresh NON-ADMIN user
     * @param req Request body passed down from the API layer to the Service layer
     * @return
     */
    @Override
    public UserResponseDto createUser(CreateUserRequest req){
        User user = createUserEntity(req);
        user.setAdmin(false);
        return userMapper.toDto(userRepo.save(user));
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User fetchUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id: " + userId));;
        return userMapper.toDto(fetchUser);
    }

    @Override
    public Page<UserResponseDto> getAllUsers(Status status, String username, String email, Pageable pageable) {
        Specification<User> specification = Specification.where((Specification<User>) null);

        // filter out using status if not null
        if (status != null) {
            specification = specification.and(UserSpecification.hasStatus(status));
        }

        // filter out using username if not null
        if (username != null) {
            specification = specification.and(UserSpecification.usernameIncludes(username));
        }

        // filter out using email if not null
        if (email != null) {
            specification = specification.and(UserSpecification.emailIncludes(email));
        }

        return userRepo.findAll(specification, pageable).map(userMapper::toDto)    ;
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

    /**
     * Create a new ADMIN/ROOT user
     * @param req
     * @return
     */
    @Override
    public UserResponseDto createAdminUser(CreateUserRequest req){
        User user = createUserEntity(req);
        user.setAdmin(true);  // set user to ADMIN
        return userMapper.toDto(userRepo.save(user));
    }

    private User createUserEntity(CreateUserRequest req){
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

        String passwordHash = passwordEncoder.encode(req.password());
        user.setPasswordHash(passwordHash);

        return user;
    }
}
