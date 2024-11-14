package com.example.youtubecount.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VIDEO_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"VIDEO_ALREADY_EXISTS", "등록하려는 영상은 이미 존재하는 영상입니다."),
    VIDEO_NOT_FOUND_ON_YOUTUBE(HttpStatus.BAD_REQUEST, "VIDEO_NOT_FOUND_ON_YOUTUBE", "등록하려는 영상이 유튜브에 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}
