package github.pedrozaz.pricealert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendAlertEmail(String to, String productName, String price, String url) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Price Alert: " + productName);
        message.setText("The price for " + productName + " has dropped to " + price + ".\n" +
                        "You can view the product here: " + url);
        emailSender.send(message);
    }
}
