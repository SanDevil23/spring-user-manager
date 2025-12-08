package com.oms.user_service.service;

import com.oms.user_service.constants.ErrorCodeEnum;
import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.dto.UserRequestDto;
import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.exception.UserServiceException;
import com.oms.user_service.helper.UserHelper;
import com.oms.user_service.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService{

    private final UserRepository userRepo;
    private final UserHelper userHelper;
    private final ModelMapper modelMapper;



    @Override
    public UserResponseDto createUser(UserRequestDto userRequest){

        userHelper.validateUserRequest(userRequest);
        log.info("Validated Incoming user Request");

        User user =  modelMapper.map(userRequest, User.class);
        log.info("Mapped usersRequest into User: {}",user);

        boolean userExists = userRepo.findByUsername(user.getUserName());
        log.info("Checked if username already exist: {}",userExists);

        if (userExists) {
            throw new UserServiceException(
                    ErrorCodeEnum.USER_ALREADY_EXIST.getErrorCode(),
                    ErrorCodeEnum.USER_ALREADY_EXIST.getErrorMessage(),
                    HttpStatus.CONFLICT
            );
        }

        User savedUser =  userRepo.save(user);
        log.info("Saved user into the DB: {}",savedUser);

        UserResponseDto userResponse =
                modelMapper.map(savedUser, UserResponseDto.class);
        log.info("Mapped user into UserResponseDto: {}",userResponse);

        return userResponse;
    }


    @Override
    public UserResponseDto getUserById(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() ->  new UserServiceException(
                        ErrorCodeEnum.USER_NOT_FOUND.getErrorCode(),
                        ErrorCodeEnum.USER_NOT_FOUND.getErrorMessage(),
                        HttpStatus.NOT_FOUND
                ));
        log.info("Queried the DB for the user with id:{} || user: {}",userId, user);

        UserResponseDto foundUser = modelMapper.map(user, UserResponseDto.class);
        log.info("Mapped foundUser into User ResponseDTO: {}",foundUser);
        return foundUser;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {

        List<User> users = userRepo.findAll();
        log.info("Gotten all users for DB: {}",users);

        List<UserResponseDto> mappedUsers =
                users.stream().map(user ->modelMapper.map(user, UserResponseDto.class))
                        .toList();
        log.info("Mapped users into UserResponseDto: {}",mappedUsers);

        return mappedUsers;
    }
}
