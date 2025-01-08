package com.example.youtubecount.dto;

import com.example.youtubecount.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserResponseDto {
    private Long id;
    private String role;
    private String email;
    private String username;

    public static UserResponseDto create(UserEntity userEntity) {
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getRole().name(),
                userEntity.getEmail(),
                userEntity.getUsername());
    }
}
