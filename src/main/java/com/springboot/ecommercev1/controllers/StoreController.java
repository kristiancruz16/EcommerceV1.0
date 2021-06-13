package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author KMCruz
 * 6/13/2021
 */

@Controller
@RequestMapping
public class StoreController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public StoreController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"","/home","/"})
    public String displayHomePage (Model model) {
        model.addAttribute("products",productService.findAll());
        return "store/homePage";
    }

    @GetMapping("/{categoryCode}/products/{productName}")
    public String showProductDetails(@PathVariable String productName, Model model) {
        model.addAttribute("product",productService.findAllByNameLikeIgnoreCase(productName));
        return "store/productDetails";
    }

    @GetMapping("/{categoryCode}")
    public String filerProductsByCategory (@PathVariable String categoryCode, Model model) {
        model.addAttribute("category",categoryService.findByCategoryCode(categoryCode));
        return "store/categoryDetails";
    }
}
