package com.example.youtubecount.service;

import com.example.youtubecount.dto.VideoDto;
import com.example.youtubecount.dto.ViewDto;
import com.example.youtubecount.entity.VideoEntity;
import com.example.youtubecount.enumType.ErrorCode;
import com.example.youtubecount.exception.CustomException;
import com.example.youtubecount.global.YouTubeAPI;
import com.example.youtubecount.repository.VideoRepository;
import com.example.youtubecount.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewCountService {
    @Autowired
    private ViewRepository viewRepository;
    @Autowired
    private VideoRepository videoRepository;

    @Value("${youtube.api.key}")
    private String apiKey;

    public List<ViewDto> getViewCount(String videoId) throws CustomException {
        VideoEntity videoEntity = getVideoEntityFromDbByVideoId(videoId);

        return getViewCountByVideoEntity(videoEntity);
    }

    private VideoEntity getVideoEntityFromDbByVideoId(String videoId) {
        List<VideoEntity> videoEntityList = videoRepository.findByVideoId(videoId);
        if (isVideoNotRegistered(videoEntityList)) {
            throw new CustomException(ErrorCode.VIDEO_NOT_REGISTERED);
        }
        return videoEntityList.get(0); // 같은 videoId는 하나만 존재하므로 0
    }

    private boolean isVideoNotRegistered(List<VideoEntity> videoEntityList) {
        return videoEntityList.isEmpty();
    }

    private List<ViewDto> getViewCountByVideoEntity(VideoEntity videoEntity) {
        return viewRepository.findByVideoEntity(videoEntity)
                .stream()
                .map(ViewDto::create)
                .collect(Collectors.toList());
    }

    public VideoDto addVideo(VideoDto dto) throws CustomException {
        String videoId = dto.getVideoId();
        if (isVideoAlreadyInDB(videoId)) {
            throw new CustomException(ErrorCode.VIDEO_ALREADY_EXISTS);
        }

        String videoName = getVideoNameFromYoutube(videoId);

        return createAndSaveVideoEntity(videoId, videoName);
    }

    private boolean isVideoAlreadyInDB(String videoId) {
        List<VideoEntity> videoEntityList = videoRepository.findByVideoId(videoId);
        return !videoEntityList.isEmpty();
    }

    private String getVideoNameFromYoutube(String videoId) throws CustomException {
        YouTubeAPI youtubeApi = new YouTubeAPI(apiKey, videoId);

        try {
            youtubeApi.loadVideoDataFromYouTube();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.VIDEO_NOT_FOUND_ON_YOUTUBE);
        }

        return youtubeApi.getVideoName();
    }

    private VideoDto createAndSaveVideoEntity(String videoId, String videoName) {
        VideoEntity videoEntity = VideoEntity.create(videoId, videoName);
        VideoEntity saved = videoRepository.save(videoEntity);
        return VideoDto.create(saved);
    }

    public VideoDto deleteVideo(VideoDto dto) throws CustomException {
        Long id = dto.getId();
        deleteVideoById(id);

        return dto;
    }

    private void deleteVideoById(Long id) throws CustomException {
        try {
            videoRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.VIDEO_NOT_REGISTERED);
        }
    }
}
