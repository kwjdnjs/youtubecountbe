package com.example.youtubecount.repository;

import com.example.youtubecount.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    @Override
    ArrayList<VideoEntity> findAll();
    ArrayList<VideoEntity> findByVideoId(String videoId);
}
