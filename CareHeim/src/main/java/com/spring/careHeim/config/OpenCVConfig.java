package com.spring.careHeim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenCVConfig {
    static {
        try {
            // Load the OpenCV native library
            nu.pattern.OpenCV.loadShared();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public void initializeOpenCV() {

    }
}
