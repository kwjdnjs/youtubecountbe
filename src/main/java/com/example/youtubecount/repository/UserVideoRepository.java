package com.example.youtubecount.repository;

import com.example.youtubecount.entity.UserVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVideoRepository extends JpaRepository<UserVideoEntity, Long> {
}
