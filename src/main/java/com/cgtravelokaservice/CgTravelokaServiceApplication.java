package com.cgtravelokaservice;

import com.cgtravelokaservice.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.thymeleaf.context.Context;

@SpringBootApplication
public class CgTravelokaServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CgTravelokaServiceApplication.class, args);
        EmailService emailService = context.getBean(EmailService.class);
        Context context1 = new Context();
        context1.setVariable("message", "Master of chemistry");
        emailService.sendMail("Test", "thanhthaohoa9999@gmail.com", context1, "email-template");
    }
}
