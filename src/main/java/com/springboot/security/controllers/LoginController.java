package com.springboot.security.controllers;

import com.springboot.security.dto.PasswordDto;
import com.springboot.security.events.PasswordResetEvent;
import com.springboot.security.events.RegistrationEvent;
import com.springboot.security.models.PasswordResetToken;
import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;
import com.springboot.security.services.PasswordResetTokenService;
import com.springboot.security.services.UserService;
import com.springboot.security.services.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

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
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(MessageSource messageSource, VerificationTokenService verificationTokenService,
                           ApplicationEventPublisher eventPublisher, UserService userService,
                           PasswordResetTokenService passwordResetTokenService, PasswordEncoder passwordEncoder) {
        this.messageSource = messageSource;
        this.verificationTokenService = verificationTokenService;
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/forgetPassword")
    public ModelAndView showForgetPasswordPage(){
        LOGGER.info("Loading Forget PasswordPage");
        return new ModelAndView("security/forgetPassword");
    }
    @PostMapping("/forgetPassword")
    public ModelAndView processForgetPassword(HttpServletRequest request, ModelMap model, @RequestParam String email){
        LOGGER.info("Processing Forget Password Page");
        Locale locale = request.getLocale();
        String appUrl = request.getContextPath();
        try{
            User user = userService.findUserByEmail(email);
            eventPublisher.publishEvent(new PasswordResetEvent(user,appUrl,locale));
        }catch (UsernameNotFoundException notFoundException){
            String error = messageSource.getMessage("error.userNotFound",null,locale);
            model.addAttribute("error",error);
            return  new ModelAndView("redirect:/login",model);
        }
        String message = messageSource.getMessage("message.resetPasswordEmail",null,locale);
        model.addAttribute("message",message);
        return new ModelAndView("redirect:/login", model);
    }
    @GetMapping("/changePassword")
    public ModelAndView showChangePasswordPage(HttpServletRequest request, ModelMap model, @RequestParam String token){
        LOGGER.info("Loading Change Password Page");
        Locale locale = request.getLocale();
        PasswordResetToken resetToken = passwordResetTokenService.findPasswordResetTokenByResetToken(token);
        if(resetToken==null){
            String error = messageSource.getMessage("error.invalidResetToken",null,locale);
            model.addAttribute("error",error);
            return  new ModelAndView("redirect:/login",model);
        }
        Calendar cal = Calendar.getInstance();
        Long tokenExpiry = resetToken.getExpiryDate().getTime();
        Long timeNow = cal.getTime().getTime();
        Long timeRemainingToExpire = tokenExpiry - timeNow;
        boolean isExpired = timeRemainingToExpire<=0;

        if(isExpired){
            String error = messageSource.getMessage("error.passwordResetLinkExpired",null,locale);
            model.addAttribute("error",error);
            return new ModelAndView("redirect:/login",model);
        }
        model.addAttribute("token",token);
        return new ModelAndView("security/changePassword",model);
    }
    @PostMapping("/changePassword")
    public ModelAndView processChangePassword(HttpServletRequest request, ModelMap model, @Valid PasswordDto passwordDto,
                                              Errors errors,@RequestParam String token){
        LOGGER.info("Processing Change Password");
        LOGGER.info(String.format("Password: %s",passwordDto.getPassword()));
        PasswordResetToken passwordResetToken = passwordResetTokenService.findPasswordResetTokenByResetToken(token);
        User user = passwordResetToken.getUser();

        if(errors.hasErrors()){
            List<ObjectError> objErrorListList =  errors.getAllErrors();
            List<String> errorList = new ArrayList<>();
            objErrorListList.stream()
                    .forEach(e->errorList.add(e.getDefaultMessage()));
            model.addAttribute("error",errorList);
            model.addAttribute("token",token);
            return new ModelAndView("security/changePassword",model);
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        user.setPasswordResetToken(null);
        userService.savedRegisteredUser(user);
        passwordResetTokenService.deletePasswordResetToken(passwordResetToken);
        String message = messageSource.getMessage("message.resetPasswordSuc",null,request.getLocale());
        model.addAttribute("message",message);
        return new ModelAndView("redirect:/login",model);
    }

}
