package com.nextgenpaper.NextGenPaper.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}