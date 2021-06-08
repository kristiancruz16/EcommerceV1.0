package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author KMCruz
 * 6/7/2021
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showAllCategory(Model model){
        model.addAttribute("categories",categoryService.findAll());
        return "categories/allCategories";
    }

    @GetMapping("/{categoryId}")
    public String showCategoryDetails (@PathVariable Long categoryId, Model model) {
        model.addAttribute("category",categoryService.findById(categoryId));
        return "categories/categoryDetails";
    }
}