package com.heartscopy.heartsocpyBeckEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.heartscopy.heartsocpyBeckEnd.repository")
@EntityScan(basePackages = "com.heartscopy.heartsocpyBeckEnd.domain")
public class HeartsocpyBeckEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartsocpyBeckEndApplication.class, args);
    }

}
