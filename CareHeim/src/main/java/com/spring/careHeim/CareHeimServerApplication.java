package com.spring.careHeim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@EnableJpaAuditing
@SpringBootApplication
public class CareHeimServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareHeimServerApplication.class, args);
    }

}
