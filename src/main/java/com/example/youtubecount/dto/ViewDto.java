package com.example.youtubecount.dto;

import com.example.youtubecount.entity.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ViewDto {
    private Long id;
    private Long videoEntityId;
    private String videoName;
    private Long view;

    public static ViewDto create(View view) {
        return new ViewDto(
                view.getId(),
                view.getVideo().getId(),
                view.getVideo().getVideoName(),
                view.getView()
        );
    }
}
