package com.example.youtubecount.repository;

import com.example.youtubecount.entity.AuthEntity;
import com.example.youtubecount.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    Optional<AuthEntity> findByRefreshToken(String refreshToken);
    boolean existsByUser(UserEntity User);
}
