package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author KMCruz
 * 6/8/2021
 */
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController controller;

    List<Product> productList;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        productList = new ArrayList<>();
        productList.add(Product.builder().id(1L).build());
        productList.add(Product.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showAllProducts() throws Exception {
        when(productService.findAll()).thenReturn(productList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/allProducts"))
                .andExpect(model().attribute("products",hasSize(2)));
    }

    @Test
    void showProductDetails() throws Exception {
        when(productService.findById(anyLong())).thenReturn(Product.builder().id(1L).build());

        mockMvc.perform(get("/products/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/productDetails"))
                .andExpect(model().attribute("product",hasProperty("id",is(1L))));
    }

    @Test
    void initializeNewProductForm () throws Exception {
        mockMvc.perform(get("/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/createOrUpdateProductForm"))
                .andExpect(model().attributeExists("product"));
    }
}