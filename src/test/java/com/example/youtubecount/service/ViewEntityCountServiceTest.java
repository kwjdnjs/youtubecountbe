package com.example.youtubecount.service;

import com.example.youtubecount.dto.VideoDto;
import com.example.youtubecount.entity.VideoEntity;
import com.example.youtubecount.enumType.ErrorCode;
import com.example.youtubecount.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ViewEntityCountServiceTest {
    @Autowired
    ViewCountService viewCountService;

    @Test
    void addVideoWithNewVideoId() {
        VideoEntity videoEntity = VideoEntity.create("TnlPtaPxXfc", "That's Life");
        VideoDto input = VideoDto.create(videoEntity);

        assertDoesNotThrow(() -> {
            VideoDto output = viewCountService.addVideo(input);
            // ID 값은 바뀌기 때문에 제외
            assertEquals(output.getVideoId(), input.getVideoId());
            assertEquals(output.getVideoName(), input.getVideoName());
        });
    }

    @Test
    void addVideoWithExistingVideoId() {
        VideoEntity videoEntity = VideoEntity.create("ut889MZ9yNo", "kuzuri");
        VideoDto input = VideoDto.create(videoEntity);

        CustomException exception = assertThrows(CustomException.class, () -> {
            viewCountService.addVideo(input);
        });

        assertEquals(ErrorCode.VIDEO_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    void addVideoWithVideoIdNotOnYoutube() {
        VideoEntity videoEntity = VideoEntity.create("ut889MZ9yN", "notFound");
        VideoDto input = VideoDto.create(videoEntity);
        CustomException exception  = assertThrows(CustomException.class, () -> {
            viewCountService.addVideo(input);
        });

        assertEquals(ErrorCode.VIDEO_NOT_FOUND_ON_YOUTUBE, exception.getErrorCode());
    }
}