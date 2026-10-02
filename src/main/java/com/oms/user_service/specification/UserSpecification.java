package com.oms.user_service.specification;

import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> hasStatus(Status status){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<User> usernameContains(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("username")),
                        "%" +  username.toLowerCase() + "%"
                );
    }

    public static Specification<User> emailContains(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%"
                );
    }
}
