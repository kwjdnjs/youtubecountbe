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
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String videoId;
    @Column
    private String videoName;


    @OneToMany(mappedBy = "video")
    private List<View> viewList = new ArrayList<>();

    public static Video createVideo(VideoDto dto) {
        return new Video(null, dto.getVideoId(), dto.getVideoName(), new ArrayList<>());
    }
}
