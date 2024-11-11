package com.example.youtubecount.controller;

import com.example.youtubecount.dto.VideoDto;
import com.example.youtubecount.dto.ViewDto;
import com.example.youtubecount.service.ViewCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ViewCountApiController {
    @Autowired
    private ViewCountService viewCountService;

    @GetMapping("/api/view")
    public ResponseEntity<List<ViewDto>> getView() {
        List<ViewDto> dtos = viewCountService.getView();
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping("/api/video")
    public ResponseEntity<?> addVideo(@RequestBody VideoDto dto) {
        try {
            VideoDto savedDto = viewCountService.addVideo(dto);
            return ResponseEntity.ok().body(savedDto);
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseEntity.badRequest().body(e);
        }
    }
}
