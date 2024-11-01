package com.example.youtubecount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class YoutubecountApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoutubecountApplication.class, args);
	}

}
