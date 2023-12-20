package com.example.ApiShop.model.dtos;

import com.example.ApiShop.model.UserRole;

import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String username, @NotNull String email,@NotNull String password, @NotNull UserRole role ) {
    
}