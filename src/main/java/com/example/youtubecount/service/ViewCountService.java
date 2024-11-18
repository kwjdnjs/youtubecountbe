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

    public List<ViewDto> getView(String videoId) throws CustomException {
        VideoEntity videoEntity = getVideoEntityFromDB(videoId);

        return getViewCountByVideoEntity(videoEntity);
    }

    private VideoEntity getVideoEntityFromDB(String videoId) {
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
        } else if (isVideoNotOnYoutube(videoId)) {
            throw new CustomException(ErrorCode.VIDEO_NOT_FOUND_ON_YOUTUBE);
        }

        return saveVideoDto(dto);
    }

    private boolean isVideoAlreadyInDB(String videoId) {
        List<VideoEntity> videoEntityList = videoRepository.findByVideoId(videoId);
        return !videoEntityList.isEmpty();
    }

    private boolean isVideoNotOnYoutube(String videoId) {
        YouTubeAPI youtubeApi = new YouTubeAPI(apiKey, videoId);
        try {
            youtubeApi.loadVideoDataFromYouTube();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private VideoDto saveVideoDto(VideoDto dto) {
        VideoEntity videoEntity = VideoEntity.createVideoEntityWithDto(dto);
        VideoEntity saved = videoRepository.save(videoEntity);
        return VideoDto.create(saved);
    }
}
