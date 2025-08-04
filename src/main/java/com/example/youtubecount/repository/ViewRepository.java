package com.example.youtubecount.repository;

import com.example.youtubecount.entity.VideoEntity;
import com.example.youtubecount.entity.ViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ViewRepository  extends JpaRepository<ViewEntity, Long> {
    @Override
    ArrayList<ViewEntity> findAll();
    ArrayList<ViewEntity> findByVideoEntity(VideoEntity videoEntity);
}
