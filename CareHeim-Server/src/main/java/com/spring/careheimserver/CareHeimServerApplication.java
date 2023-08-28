package com.spring.careheimserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class CareHeimServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareHeimServerApplication.class, args);
    }

}
