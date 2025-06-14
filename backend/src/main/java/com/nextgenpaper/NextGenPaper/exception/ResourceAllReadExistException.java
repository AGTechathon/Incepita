package com.nextgenpaper.NextGenPaper.exception;

public class ResourceAllReadExistException extends RuntimeException {
    public ResourceAllReadExistException(String usernameAlreadyTaken) {
        super(usernameAlreadyTaken);
    }
}
