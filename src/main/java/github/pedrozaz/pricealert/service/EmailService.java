package github.pedrozaz.pricealert.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;


import java.time.LocalDateTime;

@Service
public class EmailService {

    public static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;



    public void sendAlertEmail(
            String to, String productName, String currentPrice,
            String targetPrice, String productUrl, String storeName, LocalDateTime currentDate) {
    }
}
