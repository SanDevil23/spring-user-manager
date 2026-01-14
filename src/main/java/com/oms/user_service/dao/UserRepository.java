package com.oms.user_service.dao;

import com.oms.user_service.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default User filterUserById(@NotNull Long userId){
        List<User> userList = findAll();
        for (User user : userList){
            if (userId == user.getUserId()){
                return user;
            }
        }
        return null;
    }
}
