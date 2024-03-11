package com.cgtravelokaservice;

import com.cgtravelokaservice.service.implement.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.thymeleaf.context.Context;

@SpringBootApplication
public class CgTravelokaServiceApplication {

    public static void main(String[] args) {
         SpringApplication.run(CgTravelokaServiceApplication.class, args);
    }
}
