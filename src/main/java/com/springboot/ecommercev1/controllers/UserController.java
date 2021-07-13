package com.springboot.ecommercev1.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author KMCruz
 * 7/11/2021
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping("/checkout")
    public ModelAndView showCheckoutForm(HttpSession session){
        LOGGER.info("Session Id: "+session.getId());
        return new ModelAndView("/user/checkOut");
    }
}
