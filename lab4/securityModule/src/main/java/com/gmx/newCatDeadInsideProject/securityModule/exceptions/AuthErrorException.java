package com.gmx.newCatDeadInsideProject.securityModule.exceptions;

import lombok.Getter;

import java.util.Date;

@Getter
public class AuthErrorException extends Exception {
    private final int status;
    private final String message;
    private final Date timestamp;

    public AuthErrorException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}