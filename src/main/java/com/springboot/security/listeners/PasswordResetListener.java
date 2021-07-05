package com.springboot.security.listeners;

import com.springboot.security.events.PasswordResetEvent;
import com.springboot.security.models.User;
import com.springboot.security.services.PasswordResetTokenService;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author KMCruz
 * 7/4/2021
 */
@Component
public class PasswordResetListener {

    private final PasswordResetTokenService passwordResetTokenService;
    private final MessageSource messageSource;
    private final JavaMailSender mailSender;

    public PasswordResetListener(PasswordResetTokenService passwordResetTokenService, MessageSource messageSource, JavaMailSender mailSender) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.messageSource = messageSource;
        this.mailSender = mailSender;
    }

    @EventListener
    public void handlerPasswordResetEvent(PasswordResetEvent event){
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetToken(user,token);

        String recipientEmail = user.getEmail();
        String message = messageSource.getMessage("message.resetPassword",null, event.getLocale());
        String resetLink = event.getAppUrl() +
                "/login/changePassword?token="+token;
        String subject = "Password Reset";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + "\r\nhttp://localhost:8080" + resetLink);
        mailSender.send(mailMessage);
    }
}
