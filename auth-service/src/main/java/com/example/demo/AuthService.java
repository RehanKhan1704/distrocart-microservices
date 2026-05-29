package com.example.demo;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.dto.LoginRequest;
import lombok.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");

        userRepository.save(user);
    }

    public String login(LoginRequest request) {

    User user = userRepository
            .findByUsername(request.getUsername())
            .orElseThrow();

    boolean matches = passwordEncoder.matches(
            request.getPassword(),
            user.getPassword()
    );

    if (!matches) {
        throw new RuntimeException("Invalid credentials");
    }

    return jwtService.generateToken(user.getUsername());
}
}