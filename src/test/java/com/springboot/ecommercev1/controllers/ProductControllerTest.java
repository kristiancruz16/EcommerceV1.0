package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.repositories.ProductRepository;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductController controller;

    List<Product> productList;

    Category category;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();
        productList.add(Product.builder().id(1L).name("ABC").build());
        productList.add(Product.builder().id(2L).name("DEF").build());

        Set<Product> convertProductToSet = new HashSet<>();
        productList.forEach(convertProductToSet::add);

        category = Category.builder().id(1L)
                                     .products(convertProductToSet)
                                     .name("Alpha").build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();



    }

    @Test
    void showAllProducts() throws Exception {
        when(productService.findAll()).thenReturn(productList);

        mockMvc.perform(get("/admin/categories/products/showall"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/allProducts"))
                .andExpect(model().attribute("products",hasSize(2)));
    }

    @Test
    void showProductDetails() throws Exception {
        when(productService.findProductByName(anyString())).thenReturn(Product.builder().id(1L).name("Alpha").build());

        mockMvc.perform(get("/admin/categories/products").param("productName","Alpha"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/productDetails"))
                .andExpect(model().attribute("product",hasProperty("name",is("Alpha"))));
    }

    @Test
    void initializeNewProductForm () throws Exception {
        when(categoryService.findCategoryByName(anyString())).thenReturn(category);

        mockMvc.perform(get("/admin/categories/products/new").
                    param("categoryName","ABC"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/createOrUpdateProductForm"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void processNewProductForm() throws Exception {
        Product product = Product.builder().id(1L).category(category).build();
        when(categoryService.findCategoryByName(anyString())).thenReturn(category);
        when(productService.save(any())).thenReturn(product);


        mockMvc.perform(post("/admin/categories/products/new")
                    .param("sku","12345")
                    .param("name","ABC Shoes")
                    .param("productDescription","ABC Description")
                    .param("productPrice","1234.00")
                    .param("categoryName","Alpha"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/categories/?categoryName=Alpha"))
                .andExpect(model().attributeExists("product"));

        verify(productService).save(any());
    }

    @Test
    void initializeUpdateProductForm () throws Exception {

        when(productService.findProductByName(anyString()))
                .thenReturn(Product.builder().id(1L).name("ABC").build());

        mockMvc.perform(get("/admin/categories/products/edit")
                    .param("productName","ABC"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/createOrUpdateProductForm"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void processUpdateProductForm () throws Exception {

        when(productService.save(any())).thenReturn(Product.builder()
                .name("ABC").build());

        mockMvc.perform(post("/admin/categories/products/edit")
                .param("sku", "12345")
                .param("name", "ABC Shoes")
                .param("productDescription", "ABC Description")
                .param("productPrice", "1234.00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view()
                        .name("redirect:/admin/categories/products?productName=ABC"))
                .andExpect(model().attributeExists("product"));

        verify(productService).save(any());

    }
    @Test
    void createProductReturnErrorInSkuWithBlankValue () throws Exception {
        mockMvc.perform(post("/admin/categories/products/new")
                    .param("categoryName","ABC")
                    .param("sku",""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("product","sku"))
                .andExpect(view().name("products/createOrUpdateProductForm"));
    }

    @Test
    void createProductReturnErrorInSkuWithLessThanOneValue () throws Exception {
        mockMvc.perform(post("/admin/categories/products/new")
                .param("categoryName","ABC")
                .param("sku","0"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("product","sku"))
                .andExpect(view().name("products/createOrUpdateProductForm"));
    }

    @Test
    void createProductReturnErrorInProductDescriptionWithBlankValue () throws Exception {
        mockMvc.perform(post("/admin/categories/products/new")
                .param("categoryName","ABC")
                .param("productDescription",""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("product","productDescription"))
                .andExpect(view().name("products/createOrUpdateProductForm"));
    }

    @Test
    void createProductReturnErrorInProductPriceWithOutsideScopeValue () throws Exception {
        mockMvc.perform(post("/admin/categories/products/new")
                .param("categoryName","ABC")
                .param("productPrice","123456.123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("product","productPrice"))
                .andExpect(view().name("products/createOrUpdateProductForm"));
    }
}