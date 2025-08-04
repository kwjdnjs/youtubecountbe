package com.example.youtubecount.service;

import com.example.youtubecount.dto.UserRequestDto;
import com.example.youtubecount.dto.UserResponseDto;
import com.example.youtubecount.entity.UserEntity;
import com.example.youtubecount.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /** User 조회 */
    @Transactional
    public UserResponseDto findById(Long id) {
        UserEntity user = this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        return UserResponseDto.create(user);
    }

    /* User 수정 */
    /*
    @Transactional
    public void update(Long id, UserRequestDto requestDto) {
        UserEntity user = this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        user.update(requestDto);
    }
    */

    /** User 삭제 */
    @Transactional
    public void delete(Long id) {
        UserEntity user = this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + id));
        this.userRepository.delete(user);
    }
}
