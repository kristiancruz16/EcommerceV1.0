package com.springboot.ecommercev1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author KMCruz
 * 6/5/2021
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public String showAllProducts(){
        return "/products/findProduct";
    }

}
