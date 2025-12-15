package com.oms.user_service.model;

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
    private Status status;
    private Date DOB;
    private boolean isAdmin;
    private Date createdAt;
    private Date updatedAt;
}