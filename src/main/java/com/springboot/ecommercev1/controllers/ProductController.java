package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author KMCruz
 * 6/5/2021
 */
@Controller
@RequestMapping("/admin/categories")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
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

    @GetMapping("/products")
    public String showProductDetails(@RequestParam Long sku, Model model){
        model.addAttribute("product",productService.findProductBySku(sku));
        return "products/productDetails";
    }

    @GetMapping("/products/new")
    public String initializeNewProductForm (@RequestParam String categoryName, Model model) {
        Category category = categoryService.findCategoryByName(categoryName);
        Product product = new Product();
        category.getProducts().add(product);
        product.setCategory(category);
        model.addAttribute("product",product);
        return CREATE_OR_UPDATE_PRODUCT_FORM_VIEW;
    }

    @PostMapping("/products/new")
    public String processNewProductForm(@RequestParam String categoryName,
                                        @Valid Product product, BindingResult result) {
        if(productService.existsProductBySku(product)) {
            result.rejectValue("sku", "duplicate", "already exists");
        }
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_PRODUCT_FORM_VIEW;
        }
        LOGGER.info(categoryName);
        Category currentCategory = categoryService.findCategoryByName(categoryName);
        product.setCategory(currentCategory);
        currentCategory.getProducts().add(product);
        productService.save(product);
        return "redirect:/admin/categories/?categoryName="+currentCategory.getName();
    }

    @GetMapping("/products/edit")
    public String initializeUpdateProductForm(@RequestParam Long sku, Model model) {
        model.addAttribute("product",productService.findProductBySku(sku));
        return CREATE_OR_UPDATE_PRODUCT_FORM_VIEW;
    }

    @PostMapping("/products/edit")
    public String processUpdateProductForm (@Valid Product product, BindingResult result) {
        if(productService.existsProductBySku(product)) {
                result.rejectValue("sku", "duplicate", "already exists");
        }

        if(result.hasErrors()){
            return CREATE_OR_UPDATE_PRODUCT_FORM_VIEW;
        }
        Product savedProduct = productService.save(product);
        return "redirect:/admin/categories/products?productName="+savedProduct.getName();


    }


}
