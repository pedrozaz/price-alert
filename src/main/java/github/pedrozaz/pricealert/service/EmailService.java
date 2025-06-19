package github.pedrozaz.pricealert.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService {

    public static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailService(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Sends an alert email to the user when the product price drops below the target price.
     *
     * @param to            The recipient's email address.
     * @param alertData     The data containing product information and price details.
     */

    public void sendAlertEmail(String to, Map<String, Object> alertData) {
        try {

            Context context = new Context();
            context.setVariables(alertData);

            String htmlContent = templateEngine.process("alert-email", context);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setFrom(senderEmail);
            helper.setSubject("Price Alert: Product Price Dropped!");
            helper.setText(htmlContent, true);

            emailSender.send(message);
            log.info("Alert email sent to {}", to);

        } catch (Exception e) {
            log.error("Error creating email context: {}", e.getMessage());
            return;
        }
    }
}
