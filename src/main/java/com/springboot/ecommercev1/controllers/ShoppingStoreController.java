package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author KMCruz
 * 6/13/2021
 */

@Controller
@RequestMapping
public class ShoppingStoreController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public ShoppingStoreController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"","/home","/"})
    public String displayHomePage (Model model) {
        model.addAttribute("categories",categoryService.findAll());
        return "shoppingStore/homePage";
    }
}
