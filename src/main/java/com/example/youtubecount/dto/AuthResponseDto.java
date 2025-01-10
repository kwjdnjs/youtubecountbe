package com.example.youtubecount.dto;

import com.example.youtubecount.entity.AuthEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String username;

    @Builder
    public AuthResponseDto(AuthEntity entity) {
        tokenType = entity.getTokenType();
        accessToken = entity.getAccessToken();
        refreshToken = entity.getRefreshToken();
        username = entity.getUser().getUsername();
    }
}