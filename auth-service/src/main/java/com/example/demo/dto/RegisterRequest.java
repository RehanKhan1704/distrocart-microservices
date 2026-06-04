package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (

   @Schema(description = "USername", example = "example123")
   @NotBlank(message = "Username is required")
   @Size(min = 4, max = 50)
   String username,
   @Schema(description = "Alphanumeric password", example = "123example")
   @NotBlank(message = "Password is required")
   @Size(min = 6)
   String password
){
}