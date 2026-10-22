
package com.oms.user_service.controller;
import com.oms.user_service.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.oms.user_service.dto.CreateUserRequest;
import com.oms.user_service.dto.UpdateUserRequest;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.service.IUserService;
import com.oms.user_service.util.Status;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "User Management",
        description = "Create, retrieve, update, delete, and manage user accounts"
)
public class UserController {

    private final IUserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    @Operation(
            summary = "Create a user",
            description = "Creates a regular user account. Requires administrator privileges."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Administrator privileges required"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody CreateUserRequest req) {

        log.info("Received request to create a user");
        UserResponseDto savedUser = userService.createUser(req);
        log.info("User created with id: {}", savedUser.getId());

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a single user by ID. Requires administrator privileges."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Administrator privileges required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDto> getUserById(
            @Parameter(description = "ID of the user to retrieve", example = "1")
            @PathVariable("userId") Long userId) {

        log.info("Getting user with id: {}", userId);
        UserResponseDto user = userService.getUserById(userId);
        log.info("Found user with id: {}", user.getId());

        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(
            summary = "List users",
            description = """
            Retrieves a paginated list of users. Results can be filtered by
            account status, email, and username. Requires administrator privileges.

            Example: /users?status=ACTIVE&username=john&page=0&size=10
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid filter or pagination parameter", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Administrator privileges required")
    })
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @Parameter(description = "Filter by account status")
            @RequestParam(required = false) Status status,

            @Parameter(description = "Filter by email")
            @RequestParam(required = false) String email,

            @Parameter(description = "Filter by username")
            @RequestParam(required = false) String username,

            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        log.info(
                "Retrieving users - page: {}, size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<UserResponseDto> users =
                userService.getAllUsers(status, username, email, pageable);

        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes the specified user account. Requires administrator privileges."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Administrator privileges required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDto> deleteUser(
            @Parameter(description = "ID of the user to delete", example = "1")
            @PathVariable("userId") Long userId) {

        log.info("Deleting user with id: {}", userId);
        UserResponseDto user = userService.deleteUserById(userId);
        log.info("User deleted successfully: {}", userId);

        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    @Operation(
            summary = "Clear all users",
            description = """
            Deletes all user records. This is a destructive operation and should
            be used with extreme caution. Requires administrator privileges.
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User database cleared"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Administrator privileges required")
    })
    public ResponseEntity<String> clear() {
        userService.dropUsers();
        log.info("User database cleared");

        return ResponseEntity.ok("Cleared Users");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{userId}")
    @Operation(
            summary = "Update a user",
            description = "Updates the specified user's information using the supplied request. Requires administrator privileges."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Administrator privileges required"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Updated username or email conflicts with an existing account")
    })
    public ResponseEntity<UserResponseDto> updateUser(
            @Parameter(description = "ID of the user to update", example = "1")
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest req) {

        UserResponseDto updatedUser = userService.updateUser(userId, req);
        return ResponseEntity.ok(updatedUser);
    }

    @Deprecated
    @PatchMapping("/update/status/{userId}")
    @Operation(
            summary = "Update user status (deprecated)",
            description = """
            Deprecated endpoint. Use PATCH /users/disable/{userId} or
            PATCH /users/lock/{userId} instead. This legacy endpoint currently
            does not update the user status.
            """,
            deprecated = true
    )
    @ApiResponse(
            responseCode = "200",
            description = "Legacy response; no status update is performed"
    )
    public ResponseEntity<String> updateUserStatus(
            @RequestBody UpdateUserRequest req) {

        return ResponseEntity.ok("User status updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/disable/{userId}")
    @Operation(
            summary = "Disable a user",
            description = "Sets the user's status to DISABLED. Requires administrator privileges."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User disabled successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Administrator privileges required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> disableUser(
            @Parameter(description = "ID of the user to disable", example = "1")
            @PathVariable Long userId) {

        userService.updateUserStatus(userId, Status.DISABLED);
        return ResponseEntity.ok("User status updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/lock/{userId}")
    @Operation(
            summary = "Lock a user",
            description = "Sets the user's status to LOCKED. Requires administrator privileges."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User locked successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Administrator privileges required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> lockUser(
            @Parameter(description = "ID of the user to lock", example = "1")
            @PathVariable Long userId) {

        userService.updateUserStatus(userId, Status.LOCKED);
        return ResponseEntity.ok("User status updated successfully");
    }
}
