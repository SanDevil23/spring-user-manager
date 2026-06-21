package com.oms.user_service.controller;

import com.oms.user_service.dto.CreateUserRequestDto;
import com.oms.user_service.dto.UpdateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.model.User;
import com.oms.user_service.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto req){
        log.info("Received user data to be processed: {}", req );
        User savedUser = userService.createUser(req);
        log.info("User has been saved with id: {}", savedUser.getUserId());
        log.info("User data: {}", savedUser);
        return new ResponseEntity<>(savedUser.toDto(savedUser), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId){
        log.info("Getting user with id: {}", userId);
        User user = userService.getUserById(userId);
        log.info("Gotten user with id: {}",user.getUserId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("Retrieving all users");
        List<User> users = userService.getAllUsers();
        log.info("Retrieved all users");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId){
        User user = userService.deleteUserById(userId);
        if (user==null){
            log.warn("User not found in the database");
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.NOT_FOUND);
        }
        log.info("User deleted successfully");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> clear(){
        userService.dropUsers();
        log.info("User database cleared");
        return new ResponseEntity<>("Cleared Users", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser){
        User storedUser = userService.updateUser(updatedUser);
        if (storedUser.equals(updatedUser)){
            log.info("User data updated successfully");
            return new ResponseEntity<>(storedUser, HttpStatus.OK);
        }
        log.error("Failed to update user data");
        return new ResponseEntity<>(storedUser, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("/update/status")
    public ResponseEntity<String> updateUserStatus(@RequestBody UpdateUserRequest req){
        long id = req.getUserId();
        String state = req.getStatus();

        userService.updateUserStatus(id, state);

        return new ResponseEntity<>("User status updated successfully", HttpStatus.OK);
    }
}
