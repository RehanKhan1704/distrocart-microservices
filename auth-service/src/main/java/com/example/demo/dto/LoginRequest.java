package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
     @Schema(description = "Username",example = "YourName")
     @NotBlank(message = "Username is required")
     String username,
     @Schema(description = "Alphanumeric password", example = "Name123")
     @NotBlank(message = "Password can be empty")
     String password
){
}