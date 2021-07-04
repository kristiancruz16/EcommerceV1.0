package com.springboot.security.listeners;

import com.springboot.security.events.RegistrationEvent;
import com.springboot.security.models.User;
import com.springboot.security.services.VerificationTokenService;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author KMCruz
 * 7/2/2021
 */
@Component
public class RegistrationListener {

    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    public RegistrationListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender,
                                MessageSource messageSource) {
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
        this.messageSource = messageSource;
    }

    @EventListener
    public void handlerRegistrationEvent(RegistrationEvent event){
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.createVerificationToken(user,token);

        String recipientEmailAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationLink = event.getAppUrl() +
                "/login/registerConfirm?token=" +token;
        String message =messageSource.getMessage("message.registrationSuccessLink",null,event.getLocale());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmailAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + "\r\nhttp://localhost:8080" + confirmationLink);

        mailSender.send(mailMessage);

    }
}
