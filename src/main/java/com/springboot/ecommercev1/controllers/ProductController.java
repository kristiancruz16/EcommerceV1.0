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
 * 6/5/2021
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showAllProducts(Model model){
        model.addAttribute("products",productService.findAll());
        return "products/allProducts";
    }

    @GetMapping("/{productId}")
    public String showProductDetails(@PathVariable Long productId, Model model){
        model.addAttribute("product",productService.findById(productId));
        return "products/productDetails";
    }


}
