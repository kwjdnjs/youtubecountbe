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
        VideoDto input = VideoDto.createVideoDto(new Video(3L,
                "TnlPtaPxXfc",
                "That's Life",
                new ArrayList<>()));

        assertDoesNotThrow(() -> {
            VideoDto output = viewCountService.addVideo(input);
            assertEquals(output.toString(), input.toString());
        });
    }

    @Test
    void addVideoWithExistingVideoId() {
        VideoDto input = VideoDto.createVideoDto(new Video(3L,
                "ut889MZ9yNo",
                "kuzuri",
                new ArrayList<>()));

            Exception exception  = assertThrows(Exception.class, () -> {
                viewCountService.addVideo(input);
            });

            assertEquals("The video is already registered", exception.getMessage());
    }

    @Test
    void addVideoWithVideoIdNotOnYoutube() {
        VideoDto input = VideoDto.createVideoDto(new Video(3L,
                "ut889MZ9yN",
                "notFound",
                new ArrayList<>()));
        Exception exception  = assertThrows(Exception.class, () -> {
            viewCountService.addVideo(input);
        });

        assertEquals("The video does not exist on YouTube", exception.getMessage());
    }
}