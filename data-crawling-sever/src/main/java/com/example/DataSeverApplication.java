package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;

import com.example.service.CrawlingService;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class DataSeverApplication {
    private final CrawlingService crawlingService;

    public static void main(String[] args) {
        SpringApplication.run(DataSeverApplication.class, args);
    }

    //1000*60*60*24*7 = 60,480,000 = 1주일
    @Scheduled(fixedDelay = 60480000)
    public void executeTask() {
        /*
         * Task 실행 시 처음 시작은 모든 뮤지컬 정보를 가져오기 때문에 시간이 오래걸립니다.
         * 처음 1번 실행할 때만 그대로 실행하시고 이후부터는 MusicalService안에 있는 isFirst를 false로 바꾸고 실행해주세요
         * */
        crawlingService.task();

    }
}
