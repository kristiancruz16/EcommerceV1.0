package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author KMCruz
 * 6/5/2021
 */
@Controller
@RequestMapping("/categories")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private static final String CREATE_OR_UPDATE_PRODUCT_FORM_VIEW = "products/createOrUpdateProductForm";

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/showall")
    public String showAllProducts(Model model){
        model.addAttribute("products",productService.findAll());
        return "products/allProducts";
    }

    @GetMapping("/{categoryId}/products/{productId}")
    public String showProductDetails(@PathVariable Long productId, Model model){
        model.addAttribute("product",productService.findById(productId));
        return "products/productDetails";
    }

    @GetMapping("/{categoryId}/products/new")
    public String initializeNewProductForm (@PathVariable Long categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        Product product = new Product();
        category.getProducts().add(product);
        product.setCategory(category);
        model.addAttribute("product",product);
        return CREATE_OR_UPDATE_PRODUCT_FORM_VIEW;
    }

    @PostMapping("/{categoryId}/products/new")
    public String processNewProductForm(@PathVariable Long categoryId, Product product,  BindingResult result) {
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_PRODUCT_FORM_VIEW;
        }
        Category currentCategory = categoryService.findById(categoryId);
        product.setCategory(currentCategory);
        currentCategory.getProducts().add(product);
        productService.save(product);
        return "redirect:/categories/" +categoryId;
    }

    @GetMapping("/{categoryId}/products/{productId}/edit")
    public String initializeUpdateProductForm(@PathVariable Long productId, Model model) {
        model.addAttribute("product",productService.findById(productId));
        return CREATE_OR_UPDATE_PRODUCT_FORM_VIEW;
    }

}
