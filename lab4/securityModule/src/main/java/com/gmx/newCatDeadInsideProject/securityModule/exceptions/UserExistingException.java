package com.gmx.newCatDeadInsideProject.securityModule.exceptions;

public class UserExistingException extends RuntimeException {

    public UserExistingException(String message) {
        super(message);
    }

    public UserExistingException(String message, Throwable cause) {
        super(message, cause);
    }
}
