package com.nextgenpaper.NextGenPaper.service;

import com.nextgenpaper.NextGenPaper.dto.auth.LoginRequest;
import com.nextgenpaper.NextGenPaper.dto.auth.RegisterRequest;
import com.nextgenpaper.NextGenPaper.entity.User;
import com.nextgenpaper.NextGenPaper.exception.ResourceAllReadExistException;
import com.nextgenpaper.NextGenPaper.exception.ResourceNotFoundException;
import com.nextgenpaper.NextGenPaper.repository.UserRepository;
import com.nextgenpaper.NextGenPaper.service.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;

    private final AuthenticationManager authManager;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String register(RegisterRequest newUser) {
        if(userRepository.existsByUsername(newUser.getUsername())) {
            throw new ResourceAllReadExistException("User All Ready exist");
        }

        User user = new User(
                newUser.getUsername(),
                encoder.encode(newUser.getPassword())
        );
        user.setUserId(UUID.randomUUID().toString());
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "Registered Successfully";
    }

    public String verify(LoginRequest user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        throw new ResourceNotFoundException("User not found");
    }

    public User getUser(String token) {
        String username = jwtService.extractUserName(token);
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }
}
