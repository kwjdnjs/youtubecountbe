package com.example.youtubecount.configuration;

import com.example.youtubecount.entity.Video;
import com.example.youtubecount.repository.VideoRepository;
import com.example.youtubecount.repository.ViewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(VideoRepository videoRepository, ViewRepository viewRepository) {
        return args -> {
            videoRepository.save(Video.create("ut889MZ9yNo", "kuzuri"));
            videoRepository.save(Video.create("Atvsg_zogxo", "study me"));
        };
    }
}
