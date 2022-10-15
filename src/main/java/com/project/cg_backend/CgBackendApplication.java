package com.project.cg_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CgBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CgBackendApplication.class, args);
    }

}
