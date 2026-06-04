package com.example.demo.dto;

import com.example.demo.entity.Role;

public record LoginResponse(
        String token,
        String username,
        Role role
) {}