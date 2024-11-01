package com.example.youtubecount.repository;

import com.example.youtubecount.entity.View;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ViewRepository  extends CrudRepository<View, Long> {
    @Override
    ArrayList<View> findAll();
}
