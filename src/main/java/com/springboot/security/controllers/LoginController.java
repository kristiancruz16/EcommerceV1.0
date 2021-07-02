package com.springboot.security.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public LoginController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView showCustomLoginForm(HttpServletRequest request, ModelMap model,
                                            @RequestParam Optional<String> message, @RequestParam Optional<String> error){
        LOGGER.info("Show Login Form");
        Locale locale = request.getLocale();
        model.addAttribute("lang",locale.getLanguage());
        message.ifPresent(msg->model.addAttribute("message",msg));
        error.ifPresent(err->model.addAttribute("error",err));
        return new ModelAndView("security/login",model);
    }
}
