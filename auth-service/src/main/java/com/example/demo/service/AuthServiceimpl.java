package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.LoginRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceimpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.findByUsername(request.username()).isPresent()){
            throw new UserAlreadyExistException(request.username());
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setRole(Role.USER);
        User saved = userRepository.save(user);
        return new RegisterResponse(
            saved.getId(),
            saved.getUsername(),
            saved.getRole()
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {
    User user = userRepository
            .findByUsername(request.username())
            .orElseThrow(() -> new UserDoesNotExistException(request.username()));
    boolean matches = passwordEncoder.matches(
            request.password(),
            user.getPassword()
    );
    if (!matches) {
        throw new InvalidCredentialsException();
    }
    String token = jwtService.generateToken(user.getUsername());
    return new LoginResponse(
        token,
        user.getUsername(),
        user.getRole()
    );
}
}