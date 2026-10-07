package com.oms.user_service.service;

import com.oms.user_service.dao.UserRepository;
import com.oms.user_service.model.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String userId){
        User user = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getId().toString())
                .password(user.getPasswordHash())
                .authorities(
                        user.isAdmin()? "ROLE_ADMIN" : "USER_ROLE"
                )
                .build();
    }
}
