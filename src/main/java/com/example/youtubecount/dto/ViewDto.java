package com.example.youtubecount.dto;

import com.example.youtubecount.entity.ViewEntity;
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

    public static ViewDto create(ViewEntity viewEntity) {
        return new ViewDto(
                viewEntity.getId(),
                viewEntity.getVideoEntity().getId(),
                viewEntity.getVideoEntity().getVideoName(),
                viewEntity.getView()
        );
    }
}
