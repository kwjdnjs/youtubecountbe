package com.example.youtubecount.dto;

import com.example.youtubecount.entity.UserEntity;

public class UserResponseDto {
    private Long id;
    private String role;
    private String email;
    private String username;

    public UserResponseDto(UserEntity entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
        // Enum -> String
        this.role = entity.getRole().name();
    }
}
