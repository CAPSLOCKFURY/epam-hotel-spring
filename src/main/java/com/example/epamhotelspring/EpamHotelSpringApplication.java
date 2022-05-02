package com.example.epamhotelspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EpamHotelSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpamHotelSpringApplication.class, args);
    }

}
