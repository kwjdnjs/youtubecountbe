package com.example.youtubecount.service;

import com.example.youtubecount.dto.VideoDto;
import com.example.youtubecount.dto.ViewDto;
import com.example.youtubecount.entity.Video;
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

    public List<ViewDto> getView() {
        return viewRepository.findAll()
                .stream()
                .map(ViewDto::create)
                .collect(Collectors.toList());
    }

    public VideoDto addVideo(VideoDto dto) throws Exception {
        String videoId = dto.getVideoId();
        if (isVideoAlreadyInDB(videoId)) {
            throw new Exception("The video is already registered");
        } else if (isVideoNotOnYoutube(videoId)) {
            throw new Exception("The video does not exist on YouTube");
        } else {
            return saveVideoDto(dto);
        }
    }

    private boolean isVideoAlreadyInDB(String videoId) {
        List<Video> videoList = videoRepository.findByVideoId(videoId);
        return !videoList.isEmpty();
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
        Video video = Video.createVideoWithDto(dto);
        Video saved = videoRepository.save(video);
        return VideoDto.create(saved);
    }
}
