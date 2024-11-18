package com.example.youtubecount.Scheduler;

import com.example.youtubecount.entity.VideoEntity;
import com.example.youtubecount.entity.ViewEntity;
import com.example.youtubecount.global.YouTubeAPI;
import com.example.youtubecount.repository.VideoRepository;
import com.example.youtubecount.repository.ViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ViewCountUpdateScheduler {
    @Value("${youtube.api.key}")
    private String apiKey;

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ViewRepository viewRepository;

    //TEST UPDATE_RATE
    final int UPDATE_RATE = 10000;

    @Scheduled(fixedRate = UPDATE_RATE)
    public void updateViewCount() {
        List<VideoEntity> videoEntityEntityList = getVideoEntityListFromDB();
        for(VideoEntity videoEntity : videoEntityEntityList) {
            updateViewCountInDB(videoEntity);
        }
    }

    private List<VideoEntity> getVideoEntityListFromDB() {
        return videoRepository.findAll();
    }

    private void updateViewCountInDB(VideoEntity videoEntity) {
        try {
            Long viewCount = getViewCountFromYouTube(videoEntity.getVideoId());
            ViewEntity viewEntity = ViewEntity.create(videoEntity, viewCount);
            saveViewEntityInDB(viewEntity);
        } catch (Exception e) {
            log.warn(e.toString());
        }
    }

    private Long getViewCountFromYouTube(String videoId) throws IOException {
        YouTubeAPI youTubeAPI = new YouTubeAPI(apiKey, videoId);
        youTubeAPI.loadVideoDataFromYouTube();
        return youTubeAPI.getViewCount();
    }

    private void saveViewEntityInDB(ViewEntity viewEntity) {
        viewRepository.save(viewEntity);
    }
}
