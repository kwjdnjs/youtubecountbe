package com.example.youtubecount.configuration;

import com.example.youtubecount.entity.Video;
import com.example.youtubecount.entity.View;
import com.example.youtubecount.repository.VideoRepository;
import com.example.youtubecount.repository.ViewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(VideoRepository videoRepository, ViewRepository viewRepository) {
        return args -> {
          videoRepository.save(new Video(null, "ut889MZ9yNo", "kuzuri", new ArrayList<>()));
          videoRepository.save(new Video(null, "Atvsg_zogxo", "study me", new ArrayList<>()));
          Video video1 = videoRepository.findById(1L).orElseThrow();
          Video video2 = videoRepository.findById(2L).orElseThrow();
          viewRepository.save(new View(null,video1, 0L));
          viewRepository.save(new View(null, video2, 0L));
        };
    }
}
