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
    private static final String CREATE_OR_UPDATE_CATEGORY_FORM_VIEW = "categories/createOrUpdateCategoryForm";

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
        return CREATE_OR_UPDATE_CATEGORY_FORM_VIEW;
    }

    @PostMapping("/new")
    public String processNewCategoryForm(Category category, BindingResult result) {
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_CATEGORY_FORM_VIEW;
        } else {
            Category savedCategory = categoryService.save(category);
            return "redirect:/categories/" + savedCategory.getId();
        }
    }

    @GetMapping("/{categoryId}/edit")
    public String initializeUpdateCategoryForm (@PathVariable Long categoryId, Model model) {
        model.addAttribute("category",categoryService.findById(categoryId));
        return CREATE_OR_UPDATE_CATEGORY_FORM_VIEW;
    }

}
