package com.oms.user_service.model;

import com.oms.user_service.dto.UserResponseDto;
import com.oms.user_service.util.Status;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "_user_id_sequence_generator"
    )
    @SequenceGenerator(
            name = "_user_id_sequence_generator",
            sequenceName = "_user_id_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @Id
    private long UserId;
    private String UserName;
    private String Email;

    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean isAdmin;
    private Date createdAt;
    private Date updatedAt;


    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .status(user.getStatus())
                .admin(user.isAdmin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}