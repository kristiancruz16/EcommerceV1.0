package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author KMCruz
 * 6/8/2021
 */
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductService productService;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    ProductController controller;

    List<Product> productList;

    Category category;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();
        productList.add(Product.builder().id(1L).build());
        productList.add(Product.builder().id(2L).build());

        Set<Product> convertProductToSet = new HashSet<>();

        productList.forEach(convertProductToSet::add);

        category = Category.builder().id(1L).products(convertProductToSet).build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showAllProducts() throws Exception {
        when(productService.findAll()).thenReturn(productList);

        mockMvc.perform(get("/categories/products/showall"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/allProducts"))
                .andExpect(model().attribute("products",hasSize(2)));
    }

    @Test
    void showProductDetails() throws Exception {
        when(productService.findById(anyLong())).thenReturn(Product.builder().id(1L).build());

        mockMvc.perform(get("/categories/1/products/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/productDetails"))
                .andExpect(model().attribute("product",hasProperty("id",is(1L))));
    }

    @Test
    void initializeNewProductForm () throws Exception {
        when(categoryService.findById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/categories/1/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/createOrUpdateProductForm"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void processNewProductForm() throws Exception {
        Product product = Product.builder().id(1L).category(category).build();
        when(categoryService.findById(anyLong())).thenReturn(category);
        when(productService.save(any())).thenReturn(product);

        mockMvc.perform(post("/categories/1/products/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/1"))
                .andExpect(model().attributeExists("product"));

        verify(productService).save(any());
    }

    @Test
    void initializeUpdateProductForm () throws Exception {

        when(productService.findById(anyLong())).thenReturn(Product.builder().id(1L).build());

        mockMvc.perform(get("/categories/1/products/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/createOrUpdateProductForm"))
                .andExpect(model().attributeExists("product"));
    }
}