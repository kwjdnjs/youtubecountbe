package com.example.youtubecount.dto;

import com.example.youtubecount.entity.UserEntity;
import com.example.youtubecount.enumType.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private Role role;
    private String email;
    private String username;
    private String password;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .role(this.role)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
