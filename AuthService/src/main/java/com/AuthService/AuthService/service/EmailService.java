package com.AuthService.AuthService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailService(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }

    public void sendVerificationEmail(
            String receiverEmail,
            String verificationLink
    ) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("WeCode <" + senderEmail + ">");
        message.setTo(receiverEmail);
        message.setSubject("Verify your WeCode email address");

        message.setText("""
            Hello,

            Thank you for creating a WeCode account.

            Please verify your email address using the link below:

            %s

            This link will expire in 15 minutes.

            If you did not create this account, you can ignore this email.

            Regards,
            WeCode Team
            """.formatted(verificationLink));

        mailSender.send(message);
    }
}
