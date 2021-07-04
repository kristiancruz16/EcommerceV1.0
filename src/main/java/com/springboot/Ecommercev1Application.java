package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Ecommercev1Application {

    public static void main(String[] args) {

        SpringApplication.run(Ecommercev1Application.class, args);
    }

}


