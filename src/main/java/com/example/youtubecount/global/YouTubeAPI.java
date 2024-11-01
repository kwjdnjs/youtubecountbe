package com.example.youtubecount.global;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import lombok.Getter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class YouTubeAPI {
    private String apiKey;
    private String videoId;

    private YouTube youtube;

    @Getter
    private String videoName;
    @Getter
    private Long viewCount;

    public YouTubeAPI(String apiKey, String videoId) {
        this.apiKey = apiKey;
        this.videoId = videoId;
    }

    public void loadVideoDataFromYouTube() throws IOException {
        buildYouTube();
        Video video = getVideoFromYoutube();
        setVideoNameFromVideo(video);
        setViewCountFromVideo(video);
    }

    private void buildYouTube() {
        final String APPLICATION_NAME = "My Application";
        JsonFactory jsonFactory = new JacksonFactory();

        this.youtube = new YouTube.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                jsonFactory,
                request -> {})
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Video getVideoFromYoutube() throws IOException {
        YouTube.Videos.List request = createVideoRequest();
        VideoListResponse response = request.execute();
        return getVideoFromVideoListResponse(response);
    }

    private YouTube.Videos.List createVideoRequest() throws IOException {
        final String PART = "snippet,statistics";
        YouTube.Videos.List request = youtube.videos().list(Collections.singletonList(PART));

        request.setKey(apiKey);
        request.setId(Collections.singletonList(videoId));

        return request;
    }

    private Video getVideoFromVideoListResponse(VideoListResponse response) {
        final int FIRST_VIDEO = 0;

        List<Video> videoList = response.getItems();
        return videoList.get(FIRST_VIDEO);
    }

    private void setViewCountFromVideo(Video video) {
        String viewCountStr = video.getStatistics().getViewCount().toString();
        viewCount = Long.valueOf(viewCountStr);
    }

    private void setVideoNameFromVideo(Video video) {
        videoName = video.getSnippet().getTitle();
    }
}
