package com.springboot.security.controllers;

import com.springboot.security.events.RegistrationEvent;
import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;
import com.springboot.security.services.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

/**
 * @author KMCruz
 * 7/2/2021
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final MessageSource messageSource;
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher eventPublisher;

    public LoginController(MessageSource messageSource, VerificationTokenService verificationTokenService,
                           ApplicationEventPublisher eventPublisher) {
        this.messageSource = messageSource;
        this.verificationTokenService = verificationTokenService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public ModelAndView showCustomLoginForm(HttpServletRequest request, ModelMap model,
                                            @RequestParam Optional<String> message, @RequestParam Optional<String> error,
                                            @RequestParam Optional<String> token, @RequestParam Optional<String> expired){
        LOGGER.info("Show Login Form");
        Locale locale = request.getLocale();
        model.addAttribute("lang",locale.getLanguage());
        message.ifPresent(msg->model.addAttribute("message",msg));
        error.ifPresent(err->model.addAttribute("error",err));
        token.ifPresent(tok->model.addAttribute("token",tok));
        expired.ifPresent(exp->model.addAttribute("expired",expired));
        LOGGER.info(token.toString());
        return new ModelAndView("security/login",model);
    }

    @GetMapping("/resendVerificationLink")
    public ModelAndView processResendVerificationLink(HttpServletRequest request, ModelMap model,
                                                      @RequestParam String token) {
        LOGGER.info("Resending Verification Link");
        Locale locale = request.getLocale();
        String appUrl = request.getContextPath();
        VerificationToken vToken =  verificationTokenService.findVerificationTokenByRegistrationToken(token);
        User user = vToken.getUser();
        eventPublisher.publishEvent(new RegistrationEvent(user,appUrl,locale));
        String message = messageSource.getMessage("message.registrationSuccess",null,locale);
        model.addAttribute("message",message);
        model.addAttribute("token",token);
        return new ModelAndView("security/login",model);
    }
}
