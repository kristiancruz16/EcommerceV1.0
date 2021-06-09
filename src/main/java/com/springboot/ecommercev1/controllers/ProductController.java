package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.domain.Product;
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
@RequestMapping("/categories")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

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
    public String initializeNewProductForm (Category category, Model model) {
        Product product = new Product();
        category.getProducts().add(product);
        product.setCategory(category);
        model.addAttribute("product",product);
        return "products/createOrUpdateProductForm";
    }

  /*  @PostMapping("/new")
    public String processNewProductForm()*/

}
