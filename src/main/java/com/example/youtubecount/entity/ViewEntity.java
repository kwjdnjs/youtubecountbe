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
public class ViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoEntity videoEntity;

    @Column
    private Long viewCount;

    public static ViewEntity create(VideoEntity videoEntity, Long viewCount) {
        return new ViewEntity(null, videoEntity, viewCount);
    }
}
