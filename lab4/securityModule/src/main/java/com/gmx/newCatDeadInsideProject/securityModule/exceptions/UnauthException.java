package com.gmx.newCatDeadInsideProject.securityModule.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@NoArgsConstructor
public class UnauthException extends RuntimeException {
    public UnauthException(String message) {
        super(message);
    }

    public UnauthException(String message, Throwable cause) {
        super(message, cause);
    }
}