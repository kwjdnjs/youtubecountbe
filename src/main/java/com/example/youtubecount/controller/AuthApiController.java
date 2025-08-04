package com.example.youtubecount.controller;

import com.example.youtubecount.dto.AuthRequestDto;
import com.example.youtubecount.dto.AuthResponseDto;
import com.example.youtubecount.dto.UserRequestDto;
import com.example.youtubecount.dto.UserResponseDto;
import com.example.youtubecount.exception.CustomException;
import com.example.youtubecount.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthApiController {
    private final AuthService authService;

    /** 로그인 API */
    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto) throws CustomException {
        AuthResponseDto responseDto = authService.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /** 회원가입 API */
    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<?> singUp(@RequestBody UserRequestDto requestDto) throws CustomException {
        UserResponseDto responseDto = authService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /** 토큰갱신 API */
    @GetMapping("/api/v1/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("REFRESH_TOKEN") String refreshToken) {
        String newAccessToken = this.authService.refreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
    }
}