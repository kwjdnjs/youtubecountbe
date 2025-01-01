package com.example.youtubecount.configuration;

import com.example.youtubecount.entity.UserEntity;
import com.example.youtubecount.entity.VideoEntity;
import com.example.youtubecount.repository.UserRepository;
import com.example.youtubecount.repository.VideoRepository;
import com.example.youtubecount.repository.ViewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(VideoRepository videoRepository) {
        return args -> {
            videoRepository.save(VideoEntity.create("ut889MZ9yNo", "kuzuri"));
            videoRepository.save(VideoEntity.create("Atvsg_zogxo", "study me"));
        };
    }
}
