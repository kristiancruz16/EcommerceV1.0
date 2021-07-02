package com.springboot.security.controllers;

import com.springboot.security.dto.UserDto;
import com.springboot.security.events.RegistrationEvent;
import com.springboot.security.exceptions.UserAlreadyExistsException;
import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;
import com.springboot.security.repositories.VerificationTokenRepository;
import com.springboot.security.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author KMCruz
 * 7/1/2021
 */
@Controller
@RequestMapping("/login/registration")
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final MessageSource messageSource;
    private final VerificationTokenRepository vTokenRepository;


    public RegistrationController(UserService userService, ApplicationEventPublisher publisher, MessageSource messageSource, VerificationTokenRepository vTokenRepository) {
        this.userService = userService;
        this.publisher = publisher;
        this.messageSource = messageSource;
        this.vTokenRepository = vTokenRepository;
    }

    @GetMapping
    public ModelAndView showRegistrationForm(ModelMap model){
        UserDto userDto = new UserDto();
        model.addAttribute("user",userDto);
        return new ModelAndView("security/registration",model);
    }

    @PostMapping
    public ModelAndView processRegistrationForm(HttpServletRequest request, @Valid UserDto userDto,
                                                Errors errors){
        LOGGER.info("Processing Registration");
        Locale locale = request.getLocale();
        if(errors.hasErrors()) {
            LOGGER.warn("Error in submitted form");
            ModelAndView mav = new ModelAndView("security/registration");
            List<ObjectError> listObjectError = errors.getAllErrors();
            List<String> errorList = new ArrayList<>();
            listObjectError.stream().forEach(e->errorList.add(e.getDefaultMessage()));
            mav.addObject("message",errorList);
            return mav;
        }
        User registeredUser;
        try{
            registeredUser = userService.registerNewUser(userDto);
            String appUrl = request.getContextPath();
            publisher.publishEvent(new RegistrationEvent(registeredUser,appUrl,locale));
        }catch (UserAlreadyExistsException userExistsException){
            LOGGER.warn("Email already exists");
            ModelAndView mav = new ModelAndView("security/registration");
            String error = messageSource.getMessage("message.regError",null,locale);
            mav.addObject("message",error);
            return mav;
        }catch (RuntimeException ex) {
            throw new RuntimeException("Runtime Exception");
        }
        VerificationToken vToken = vTokenRepository.findByUser(registeredUser);
        return new ModelAndView("security/registration","token",vToken.getRegToken());
    }
}
