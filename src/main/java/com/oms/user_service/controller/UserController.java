package com.oms.user_service.controller;

import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.UpdateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.service.IUserService;
import com.oms.user_service.util.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequest req){
        log.info("Received user data to be processed: {}", req );
        UserResponseDto savedUser = userService.createUser(req);
        log.info("User has been saved with id: {}", savedUser.getId());
        log.info("User data: {}", savedUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") Long userId){
        log.info("Getting user with id: {}", userId);
        UserResponseDto user = userService.getUserById(userId);
        log.info("Found user with id: {}", user.getId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //TODO: design business layer logic to return UserResponseDTO in this API
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable){
        log.info("Retrieving users - page: {}, size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<UserResponseDto> users =
                userService.getAllUsers(status, username, email , pageable);

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable("userId") Long userId){
        log.info("Deleting user with id : {}", userId);
        UserResponseDto user = userService.deleteUserById(userId);
        log.info("User deleted successfully: {}", userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> clear(){
        userService.dropUsers();
        log.info("User database cleared");
        return new ResponseEntity<>("Cleared Users", HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId,@Valid @RequestBody UpdateUserRequest req){
        UserResponseDto updatedUser = userService.updateUser(userId, req);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Deprecated
    @PatchMapping("/update/status/{userId}")
    public ResponseEntity<String> updateUserStatus(@RequestBody UpdateUserRequest req){
//        long id = req.getUserId();
//        String state = req.getStatus();
//        userService.updateUserStatus(id, state);
        return new ResponseEntity<>("User status updated successfully", HttpStatus.OK);
    }
}
