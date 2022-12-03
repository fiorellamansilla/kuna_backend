package com.kuna_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KunaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KunaBackendApplication.class, args);
    }

}

