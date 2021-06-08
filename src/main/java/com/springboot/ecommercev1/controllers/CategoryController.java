package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/new")
    public String initializeNewCategoryForm(Model model){
        model.addAttribute("category", Category.builder().build());
        return "categories/createOrUpdateCategoryForm";
    }

    @PostMapping("/new")
    public String processNewCategoryForm(Category category, BindingResult result) {
        if(result.hasErrors()){
            return "categories/createOrUpdateCategoryForm";
        } else {
            Category savedCategory = categoryService.save(category);
            return "redirect:/categories/" + savedCategory.getId();
        }

    }


}
