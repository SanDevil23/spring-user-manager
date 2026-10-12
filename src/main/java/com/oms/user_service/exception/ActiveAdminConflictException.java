package com.oms.user_service.exception;

public class ActiveAdminConflictException extends RuntimeException {
    public ActiveAdminConflictException(String message) {
        super(message);
    }
}
