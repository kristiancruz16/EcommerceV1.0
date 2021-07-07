package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author KMCruz
 * 6/7/2021
 */
@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
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

    @GetMapping("/")
    public String showCategoryDetails (@RequestParam String categoryName, Model model) {
        model.addAttribute("category",categoryService.findCategoryByName(categoryName));
        return "categories/categoryDetails";
    }

    @GetMapping("/new")
    public String initializeNewCategoryForm(Model model){
        model.addAttribute("category", Category.builder().build());
        return CREATE_OR_UPDATE_CATEGORY_FORM_VIEW;
    }

    @PostMapping("/new")
    public String processNewCategoryForm(@Valid Category category, BindingResult result) {


        if (categoryService.existsByNameIgnoreCase(category.getName())) {
            result.rejectValue("name","duplicate","already exists");
        }

        if(result.hasErrors()){
            return CREATE_OR_UPDATE_CATEGORY_FORM_VIEW;
        }
        Category savedCategory = categoryService.save(category);
        return "redirect:/admin/categories/?categoryName=" + savedCategory.getName();
    }

    @GetMapping("/edit")
    public String initializeUpdateCategoryForm (@RequestParam String categoryName, Model model) {
        model.addAttribute("category",categoryService.findCategoryByName(categoryName));
        return CREATE_OR_UPDATE_CATEGORY_FORM_VIEW;
    }

    @PostMapping("/edit")
    public String processUpdateCategoryForm(@Valid Category category, BindingResult result) {
        if (categoryService.existsByNameIgnoreCase(category.getName())) {
            result.rejectValue("name","duplicate","already exists");
        }
        if (result.hasErrors()) {
            return CREATE_OR_UPDATE_CATEGORY_FORM_VIEW;
        }
        Category savedCategory = categoryService.save(category);
        return "redirect:/admin/categories/?categoryName="+savedCategory.getName();

    }

}
