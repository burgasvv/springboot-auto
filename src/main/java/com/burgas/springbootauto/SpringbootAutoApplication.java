package com.burgas.springbootauto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootAutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAutoApplication.class, args);
    }
}
