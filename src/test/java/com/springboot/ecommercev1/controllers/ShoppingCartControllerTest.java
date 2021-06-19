package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import com.springboot.ecommercev1.services.ShoppingCartLineItemService;
import com.springboot.ecommercev1.services.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author KMCruz
 * 6/19/2021
 */
@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {

    @Mock
    ShoppingCartService shoppingCartService;

    @Mock
    ShoppingCartLineItemService shoppingCartLineItemService;

    @InjectMocks
    ShoppingCartController controller;

    MockMvc mockMvc;
    ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        ShoppingCartLineItemKey key = ShoppingCartLineItemKey.builder()
                .shoppingCartId("ABC").productId(1L).build();
        shoppingCart = ShoppingCart.builder().id("ABC")
                .shoppingCartList(List.of(ShoppingCartLineItem.builder()
                                                        .id(key)
                                                        .quantity(1)
                                                        .lineAmount(1d)
                                                        .build()))
                .build();
    }

    @Test
    void showShoppingCart() throws Exception {
        when(shoppingCartService.findById(anyString())).thenReturn(shoppingCart);

        mockMvc.perform(get("/shoppingcart"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cartLineItems"))
                .andExpect(view().name("store/shoppingCart"));

    }

    @Test
    void addLineItemQuantity() throws Exception {
        Product product = Product.builder()
                            .id(1L)
                            .productPrice(2D)
                            .build();

        ShoppingCartLineItem cartLineItem = ShoppingCartLineItem.builder()
                .id(ShoppingCartLineItemKey.builder()
                        .shoppingCartId("ABC")
                        .productId(1L)
                        .build())
                .shoppingCart(shoppingCart)
                .product(product)
                .quantity(1)
                .lineAmount(1D)
                .build();

        when(shoppingCartLineItemService.findByID(any())).thenReturn(cartLineItem);
        cartLineItem = cartLineItem.addCartLineItem();
        when(shoppingCartLineItemService.save(any())).thenReturn(cartLineItem);

        mockMvc.perform(post("/shoppingcart/add")
                    .param("cartId","ABC")
                    .param("productId","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/shoppingcart"));


    }

    @Test
    void deleteLineItemQuantity() throws Exception {

        ShoppingCartLineItem cartLineItem = ShoppingCartLineItem.builder().quantity(1).build();
        when(shoppingCartLineItemService.findByID(any())).thenReturn(cartLineItem);

        shoppingCartLineItemService.save(cartLineItem);

        mockMvc.perform(post("/shoppingcart/delete")
                                .param("cartId","ABC")
                                .param("productId","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/shoppingcart"));
    }

    @Test
    void decreaseLineItemQuantity() throws Exception {

        ShoppingCartLineItem cartLineItem = ShoppingCartLineItem.builder().quantity(3).build();
        when(shoppingCartLineItemService.findByID(any())).thenReturn(cartLineItem);

        shoppingCartLineItemService.save(cartLineItem);

        mockMvc.perform(post("/shoppingcart/delete")
                .param("cartId","ABC")
                .param("productId","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/shoppingcart"));
    }

    @Test
    void deleteAllLineItems() throws Exception {
        when(shoppingCartService.findById(anyString())).thenReturn(shoppingCart);

        shoppingCartLineItemService.delete(shoppingCart);

        mockMvc.perform(post("/shoppingcart/deleteAll")
                    .param("productId","1")
                    .param("cartId","ABC"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/shoppingcart"));

    }
}