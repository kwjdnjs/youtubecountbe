package com.example.youtubecount.service;

import com.example.youtubecount.dto.VideoDto;
import com.example.youtubecount.entity.Video;
import com.example.youtubecount.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ViewCountServiceTest {
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    ViewCountService viewCountService;

    @Test
    void addVideoWithNewVideoId() {
        Video video = Video.create("TnlPtaPxXfc", "That's Life");
        VideoDto input = VideoDto.create(video);

        assertDoesNotThrow(() -> {
            VideoDto output = viewCountService.addVideo(input);
            // ID 값은 바뀌기 때문에 제외
            assertEquals(output.getVideoId(), input.getVideoId());
            assertEquals(output.getVideoName(), input.getVideoName());
        });
    }

    @Test
    void addVideoWithExistingVideoId() {
        Video video = Video.create("ut889MZ9yNo", "kuzuri");
        VideoDto input = VideoDto.create(video);

        Exception exception  = assertThrows(Exception.class, () -> {
            viewCountService.addVideo(input);
        });

        assertEquals("The video is already registered", exception.getMessage());
    }

    @Test
    void addVideoWithVideoIdNotOnYoutube() {
        Video video = Video.create("ut889MZ9yN", "notFound");
        VideoDto input = VideoDto.create(video);
        Exception exception  = assertThrows(Exception.class, () -> {
            viewCountService.addVideo(input);
        });

        assertEquals("The video does not exist on YouTube", exception.getMessage());
    }
}