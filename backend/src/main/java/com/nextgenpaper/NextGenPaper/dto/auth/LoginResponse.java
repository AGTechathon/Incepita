package com.nextgenpaper.NextGenPaper.dto.auth;

import com.nextgenpaper.NextGenPaper.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    String token;
    User user;
}
