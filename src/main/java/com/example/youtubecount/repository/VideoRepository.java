package com.example.youtubecount.repository;

import com.example.youtubecount.entity.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface VideoRepository extends CrudRepository<Video, Long> {
    @Override
    ArrayList<Video> findAll();
}
