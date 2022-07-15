package com.erstedigital.meetingappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class MeetingAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingAppBackendApplication.class, args);
    }

}
