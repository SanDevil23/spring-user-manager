
package com.oms.user_service.service;


import com.oms.user_service.dto.UserRequestDto;
import com.oms.user_service.dto.UserResponseDto;

import java.util.List;

public interface IUserService {
    UserResponseDto createUser(UserRequestDto user);
    UserResponseDto getUserById(Long userId);
    List<UserResponseDto> getAllUsers();
}
