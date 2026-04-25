package com.PriceMonitoring.AlertService.service;

import com.PriceMonitoring.AlertService.Entity.Alert;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private Resend resend;

    @Value("${EMAIL_FROM}")
    private String emailFrom;

    @Async
    public void sendMail(String email, String username, Alert alert, double currentPrice) throws ResendException {
        if(email==null || username == null){
            System.out.println("username or email is null");
        }

        String message = "Hello " + username + ",\n\n" +
                "Your alert has been triggered:\n\n" +
                "City: " + alert.getCity() + "\n" +
                "Metal: " + alert.getMetal() + "\n" +
                "Target Price: " + alert.getTargetPrice() + "\n" +
                "Current Price: " + currentPrice + "\n\n";

        var params = CreateEmailOptions.builder()
                .from(emailFrom)
                .to(email)
                .subject("Target Alert excceded")
                .html("<p>" + message + "</p>")
                .build();

        var response = resend.emails().send(params);
        System.out.println("Email sent");
    }
}