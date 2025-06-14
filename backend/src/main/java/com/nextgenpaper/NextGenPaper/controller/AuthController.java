package com.nextgenpaper.NextGenPaper.controller;


import com.nextgenpaper.NextGenPaper.dto.auth.LoginRequest;
import com.nextgenpaper.NextGenPaper.dto.auth.LoginResponse;
import com.nextgenpaper.NextGenPaper.dto.auth.RegisterRequest;
import com.nextgenpaper.NextGenPaper.entity.User;
import com.nextgenpaper.NextGenPaper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

//    Login Handler
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = userService.verify(request);
        User user  = userService.getUser(token);
        return ResponseEntity.ok(new LoginResponse(token, user));
    }

//    Register handler
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }
}
