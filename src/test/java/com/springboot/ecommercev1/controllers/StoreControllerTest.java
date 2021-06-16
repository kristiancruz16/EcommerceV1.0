package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import com.springboot.ecommercev1.services.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author KMCruz
 * 6/13/2021
 */
@ExtendWith(MockitoExtension.class)
class StoreControllerTest {

    @Mock
    CategoryService categoryService;

    @Mock
    ProductService productService;

    @Mock
    ShoppingCartService shoppingCartService;

    @InjectMocks
    StoreController controller;

    MockMvc mockMvc;

    List<Product> productList;
    Category category;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        productList = new ArrayList<>();
        productList.add(Product.builder().id(1L).build());
        productList.add(Product.builder().id(2L).build());

        Set<Product> convertProductToSet = new HashSet<>();
        productList.forEach(convertProductToSet::add);

        category = Category.builder().id(1L).products(convertProductToSet).build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    void displayHomePage() throws Exception {
        when(productService.findAll()).thenReturn(productList);
        when(categoryService.findAll()).thenReturn(List.of(category));

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("store/homePage"));
    }

    @Test
    void showProductDetails() throws Exception {
        when(productService.findById(anyLong())).thenReturn(Product.builder().id(1L).build());

        mockMvc.perform(get("/1/products/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("store/productDetails"));
    }

    @Test
    void filterProductsByCategory() throws Exception {
        when(categoryService.findById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("category"))
                .andExpect(view().name("store/categoryDetails"));

    }
/*
    @Test
    void addToCart () {
        when(productService.findById(anyLong())).thenReturn(productList.get(0));

        ShoppingCart shoppingCartToSave = ShoppingCart.builder().build();

        when(shoppingCartService.save(any())).thenReturn(shoppingCartToSave);

        ShoppingCart savedShoppingCart = shoppingCartService.save(shoppingCartToSave);
        savedShoppingCart.addShoppingCartLineItem(productList.get(0));

    }*/
}