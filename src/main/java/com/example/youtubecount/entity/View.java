package com.example.youtubecount.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Column
    private Long view;

    public static View createViewEntity(Video videoEntity, Long viewCount) {
        return new View(null, videoEntity, viewCount);
    }
}
