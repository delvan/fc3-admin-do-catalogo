package com.fullcycle.admin.catalogo.infrastructure;

import com.fullcycle.admin.catalogo.application.UseCase;
import com.fullcycle.admin.catalogo.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Word");
        SpringApplication.run(WebServerConfig.class, args);
    }
}