package com.example.demo.dto;

import com.example.demo.entity.Role;

public record RegisterResponse(
        Long id,
        String username,
        Role role
) {}