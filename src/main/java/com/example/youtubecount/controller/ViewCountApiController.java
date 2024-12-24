package com.example.youtubecount.controller;

import com.example.youtubecount.dto.VideoDto;
import com.example.youtubecount.dto.ViewDto;
import com.example.youtubecount.exception.CustomException;
import com.example.youtubecount.service.ViewCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ViewCountApiController {
    @Autowired
    private ViewCountService viewCountService;

    @GetMapping("/api/viewcount/{id}")
    public ResponseEntity<List<ViewDto>> getViewCount(@PathVariable(value="id") String id) {
        List<ViewDto> dtos = viewCountService.getViewCount(id);
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping("/api/video")
    public ResponseEntity<VideoDto> addVideo(@RequestBody VideoDto dto) throws CustomException {
        VideoDto savedDto = viewCountService.addVideo(dto);
        return ResponseEntity.ok().body(savedDto);
    }

    @DeleteMapping("/api/video")
    public ResponseEntity<VideoDto> deleteVideo(@RequestBody VideoDto dto) throws CustomException {
        VideoDto deletedDto = viewCountService.deleteVideo(dto);
        return ResponseEntity.ok().body(deletedDto);
    }
}
