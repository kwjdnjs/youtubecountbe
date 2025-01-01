package com.example.youtubecount.service;

import com.example.youtubecount.configuration.CustomUserDetails;
import com.example.youtubecount.entity.UserEntity;
import com.example.youtubecount.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;	// 별도로 생성해야 함

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다. username = " + username));
        return new CustomUserDetails(user);	// 위에서 생성한 CustomUserDetails Class
    }

    // 필요시 추가
    public UserDetails loadUserByUserId(Long userId) throws IllegalArgumentException {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. user_id = " + userId));
        return new CustomUserDetails(user);
    }

    // 필요시 추가
    public UserDetails loadUserByEmail(String email) throws IllegalArgumentException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. email = " + email));
        return new CustomUserDetails(user);
    }
}
