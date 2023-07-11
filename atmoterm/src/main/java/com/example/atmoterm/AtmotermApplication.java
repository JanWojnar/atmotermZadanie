package com.example.atmoterm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AtmotermApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmotermApplication.class, args);
    }

}
