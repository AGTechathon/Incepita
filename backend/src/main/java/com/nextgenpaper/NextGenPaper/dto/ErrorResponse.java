package com.nextgenpaper.NextGenPaper.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    String timestamp;
    int status;
    String error;
    String message;
    String path;
}
