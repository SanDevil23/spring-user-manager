package com.oms.user_service.specification;

import com.oms.user_service.model.User;
import com.oms.user_service.util.Status;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> hasStatus(Status status){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
}
