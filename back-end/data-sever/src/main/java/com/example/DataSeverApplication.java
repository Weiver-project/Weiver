package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;

import com.example.service.ActorService;
import com.example.service.MusicalService;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class DataSeverApplication {
	private final MusicalService musicalService;
	private final ActorService actorService;

	public static void main(String[] args) {
		SpringApplication.run(DataSeverApplication.class, args);
	}
	
	//1000*60*60*24*7 = 60,480,000 = 1주일
	@Scheduled(fixedDelay = 60480000)
	public void executeTask() {
		actorService.saveActor();
		
	}
}
