package com.springboot.security.controllers;

import com.springboot.security.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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

/**
 * @author KMCruz
 * 7/1/2021
 */
@Controller
@RequestMapping("/login/registration")
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

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

        if(errors.hasErrors()) {
            LOGGER.info("Forms has Errors");
            ModelAndView mav = new ModelAndView("security/registration");
            List<ObjectError> listObjectError = errors.getAllErrors();
            List<String> errorList = new ArrayList<>();
            listObjectError.stream().forEach(e->errorList.add(e.getDefaultMessage()));
            mav.addObject("message",errorList);
            return mav;
        }
        return new ModelAndView("security/registration");
    }
}
