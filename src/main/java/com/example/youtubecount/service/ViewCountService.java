package com.example.youtubecount.service;

import com.example.youtubecount.dto.ViewDto;
import com.example.youtubecount.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewCountService {
    @Autowired
    private ViewRepository viewRepository;

    public List<ViewDto> test() {
        return viewRepository.findAll()
                .stream()
                .map(ViewDto::createViewDto)
                .collect(Collectors.toList());
    }
}
