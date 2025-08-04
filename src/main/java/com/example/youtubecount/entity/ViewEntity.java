package com.example.youtubecount.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

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
    @Column
    private ZonedDateTime dateTime;

    public static ViewEntity create(VideoEntity videoEntity, Long viewCount, ZonedDateTime dateTime) {
        return new ViewEntity(null, videoEntity, viewCount, dateTime);
    }
}
