package com.example.youtubecount.entity;

import com.example.youtubecount.dto.VideoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String videoId;
    @Column
    private String videoName;


    @OneToMany(mappedBy = "videoEntity")
    private List<ViewEntity> viewEntityList = new ArrayList<>();

    public static VideoEntity create(String videoId, String videoName) {
        return new VideoEntity(null, videoId, videoName, new ArrayList<>());
    }

    public static VideoEntity createVideoEntityWithDto(VideoDto dto) {
        return new VideoEntity(null, dto.getVideoId(), dto.getVideoName(), new ArrayList<>());
    }
}
