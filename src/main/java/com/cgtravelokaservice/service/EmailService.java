package com.cgtravelokaservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Value("${spring.mail.username}")
    private String mail;

    private boolean mailSentStatus = false;

    public void sendMail(String subject, String to, Context context, String templateName) {
        MimeMessagePreparator preparation = prepare(subject, to, context, templateName);
        try {
            javaMailSender.send(preparation);
            mailSentStatus = true;
            System.out.println("Email sent successfully");
        } catch (MailException e) {
            System.out.println("Error sending email: " + e.getMessage());
            mailSentStatus = false;
        }
    }

    private MimeMessagePreparator prepare(String subject, String to, Context context, String templateName) {
        return mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(mail);
            helper.setTo(to);
            helper.setSubject(subject);
            String processedTemplate = templateEngine.process(templateName, context);
            helper.setText(processedTemplate, true);
        };
    }
}
