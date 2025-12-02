package com.oms.user_service.controller;

import com.oms.user_service.model.User;
import com.oms.user_service.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<User> createUser(@RequestBody User user){
        log.info("Received user data to be processed: {}", user);
        User savedUser = userService.createUser(user);
        log.info("User has been saved with id: {}", savedUser.getUserId());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
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
}
