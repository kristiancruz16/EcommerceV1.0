package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.*;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import com.springboot.ecommercev1.services.ShoppingCartLineItemService;
import com.springboot.ecommercev1.services.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Mock
    ShoppingCartLineItemService shoppingCartLineItemService;

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
        when(shoppingCartLineItemService.totalQuantityByShoppingCartID(anyString())).thenReturn("5");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("store/homePage"));
    }

    @Test
    void showProductDetails() throws Exception {
        when(productService.findById(anyLong())).thenReturn(Product.builder().id(1L).build());
        when(shoppingCartLineItemService.totalQuantityByShoppingCartID(anyString())).thenReturn("5");

        mockMvc.perform(get("/1/products/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("store/productDetails"));
    }

    @Test
    void filterProductsByCategory() throws Exception {
        when(categoryService.findById(anyLong())).thenReturn(category);
        when(shoppingCartLineItemService.totalQuantityByShoppingCartID(anyString())).thenReturn("5");

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("category"))
                .andExpect(view().name("store/categoryDetails"));

    }
    @Test
    void renderImageFromDatabase() throws Exception {

        Product product = Product.builder().id(1L).build();

        String mockFile = "This is a mock file";

        Byte [] fileByteObject = new Byte[mockFile.getBytes().length];

        int i = 0;

        for (byte primitiveByte : mockFile.getBytes()) {
            fileByteObject[i++] = primitiveByte;
        }

        product.setImage(fileByteObject);
        when(productService.findById(anyLong())).thenReturn(product);

        MockHttpServletResponse response = mockMvc.perform(get("/1/products/1/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte [] responseByte = response.getContentAsByteArray();

        assertTrue(Arrays.equals(mockFile.getBytes(),responseByte));

    }
    @Test
    void addToCart () throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = Product.builder().productPrice(3d).build();
        ShoppingCartLineItem cartLineItem = new ShoppingCartLineItem();

       when(productService.findById(anyLong())).thenReturn(product);
       when(shoppingCartService.save(any())).thenReturn(shoppingCart);
       when(shoppingCartLineItemService.save(any())).thenReturn(cartLineItem);

       mockMvc.perform(post("/1/products/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/1/products/1"));

       verify(shoppingCartLineItemService).save(any());

    }


}