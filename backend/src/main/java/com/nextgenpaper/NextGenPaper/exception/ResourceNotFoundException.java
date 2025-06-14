package com.nextgenpaper.NextGenPaper.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String questionPaperNotFound) {
        super(questionPaperNotFound);
    }
}
