package com.example.youtubecount.controller;

import com.example.youtubecount.dto.ViewDto;
import com.example.youtubecount.service.ViewCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ViewCountApiController {
    @Autowired
    private ViewCountService viewCountService;

    @GetMapping("/api/test")
    public ResponseEntity<List<ViewDto>> test() {
        List<ViewDto> dtos = viewCountService.test();
        return ResponseEntity.ok().body(dtos);
    }
}
