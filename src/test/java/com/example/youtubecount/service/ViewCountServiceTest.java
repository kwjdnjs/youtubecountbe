package com.example.youtubecount.service;

import com.example.youtubecount.dto.VideoDto;
import com.example.youtubecount.entity.Video;
import com.example.youtubecount.enumType.ErrorCode;
import com.example.youtubecount.exception.CustomException;
import com.example.youtubecount.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ViewCountServiceTest {
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

        CustomException exception = assertThrows(CustomException.class, () -> {
            viewCountService.addVideo(input);
        });

        assertEquals(ErrorCode.VIDEO_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    void addVideoWithVideoIdNotOnYoutube() {
        Video video = Video.create("ut889MZ9yN", "notFound");
        VideoDto input = VideoDto.create(video);
        CustomException exception  = assertThrows(CustomException.class, () -> {
            viewCountService.addVideo(input);
        });

        assertEquals(ErrorCode.VIDEO_NOT_FOUND_ON_YOUTUBE, exception.getErrorCode());
    }
}