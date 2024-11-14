package com.example.youtubecount.dto;

import com.example.youtubecount.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VideoDto {
    private Long id;
    private String videoId;
    private String videoName;

    public static VideoDto create(Video video) {
        return new VideoDto(
                video.getId(),
                video.getVideoId(),
                video.getVideoName()
        );
    }
}
