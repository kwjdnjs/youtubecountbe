package com.example.youtubecount.service;

import com.example.youtubecount.configuration.CustomUserDetails;
import com.example.youtubecount.dto.AuthRequestDto;
import com.example.youtubecount.dto.AuthResponseDto;
import com.example.youtubecount.dto.UserRequestDto;
import com.example.youtubecount.dto.UserResponseDto;
import com.example.youtubecount.entity.AuthEntity;
import com.example.youtubecount.entity.UserEntity;
import com.example.youtubecount.enumType.ErrorCode;
import com.example.youtubecount.enumType.Role;
import com.example.youtubecount.exception.CustomException;
import com.example.youtubecount.repository.AuthRepository;
import com.example.youtubecount.repository.UserRepository;
import com.example.youtubecount.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /** 로그인 */
    @Transactional
    public AuthResponseDto login(AuthRequestDto requestDto) {
        UserEntity user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NAME_NOT_FOUND));

        if (isPasswordMismatch(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(
                createUsernamePasswordAuthenticationToken(user));
        String refreshToken = jwtTokenProvider.generateRefreshToken(
                createUsernamePasswordAuthenticationToken(user));

        AuthEntity auth = null;
        if (isUserExist(user)) {
            updateUserTokens(user, accessToken, refreshToken);
            auth = user.getAuth();
        } else {
            // 최초 로그인
            auth = saveAuthEntityAndTokens(user, accessToken, refreshToken);
        }

        return new AuthResponseDto(auth);
    }

    private boolean isPasswordMismatch(String requestPassword, String savedPassword) {
        return !passwordEncoder.matches(requestPassword, savedPassword);
    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(UserEntity user) {
        return new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getPassword());
    }

    private boolean isUserExist(UserEntity user) {
        return authRepository.existsByUser(user);
    }

    private void updateUserTokens(UserEntity user, String accessToken, String refreshToken) {
        user.getAuth().setAccessToken(accessToken);
        user.getAuth().setRefreshToken(refreshToken);
    }

    private AuthEntity saveAuthEntityAndTokens(UserEntity user, String accessToken, String refreshToken) {
        return authRepository.save(AuthEntity.builder()
                .user(user)
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
    }

    /** 회원가입 */
    @Transactional
    public UserResponseDto signup(UserRequestDto requestDto) {
        requestDto.setRole(Role.ROLE_USER);
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        UserEntity user = userRepository.save(requestDto.toEntity());

        return UserResponseDto.create(user);
    }

    /** Token 갱신 */
    @Transactional
    public String refreshToken(String refreshToken) {
        // CHECK IF REFRESH_TOKEN EXPIRATION AVAILABLE, UPDATE ACCESS_TOKEN AND RETURN
        if (this.jwtTokenProvider.validateToken(refreshToken)) {
            AuthEntity auth = this.authRepository.findByRefreshToken(refreshToken).orElseThrow(
                    () -> new IllegalArgumentException("해당 REFRESH_TOKEN 을 찾을 수 없습니다.\nREFRESH_TOKEN = " + refreshToken));

            String newAccessToken = this.jwtTokenProvider.generateAccessToken(
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserDetails(auth.getUser()), auth.getUser().getPassword()));
            auth.setAccessToken(newAccessToken);
            return newAccessToken;
        }

        // IF NOT AVAILABLE REFRESH_TOKEN EXPIRATION, REGENERATE ACCESS_TOKEN AND REFRESH_TOKEN
        // IN THIS CASE, USER HAVE TO LOGIN AGAIN, SO REGENERATE IS NOT APPROPRIATE
        return null;
    }
}
