package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.*;
import com.springboot.ecommercev1.services.*;
import com.springboot.security.models.CustomUser;
import com.springboot.security.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
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

    @Mock
    CustomerService customerService;

    @InjectMocks
    StoreController controller;

    MockMvc mockMvc;

    List<Product> productList;
    Category category;
    Customer customer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        productList = new ArrayList<>();
        productList.add(Product.builder().id(1L).build());
        productList.add(Product.builder().id(2L).build());

        Set<Product> convertProductToSet = new HashSet<>();
        productList.forEach(convertProductToSet::add);

        category = Category.builder().id(1L).name("ABC").products(convertProductToSet).build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        customer = Customer.builder().id(1L)
                        .shoppingCart(ShoppingCart.builder().id(1L).shoppingCartList(Arrays.asList(ShoppingCartLineItem.builder().quantity(5).build()))
                                .build())
                   .build();

    }

    @Test
    void displayHomePage() throws Exception {
//        when(customerService.findLoggedInCustomer(any())).thenReturn(customer);
        when(productService.findAll()).thenReturn(productList);
        when(categoryService.findAll()).thenReturn(List.of(category));
//        when(shoppingCartLineItemService.totalQuantityByShoppingCartID(anyLong())).thenReturn("5");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(view().name("store/homePage"));
    }

  /*  @Test
    void showProductDetails() throws Exception {
        when(customerService.findLoggedInCustomer(any())).thenReturn(Optional.of(customer));
        when(productService.findProductBySku(anyLong())).thenReturn(Product.builder().id(1L).sku(1L).build());
        when(shoppingCartLineItemService.totalQuantityByShoppingCartID(anyLong())).thenReturn("5");

        mockMvc.perform(get("/categories/products")
                        .param("sku","1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("store/productDetails"));
    }

    @Test
    void filterProductsByCategory() throws Exception {
        when(customerService.findLoggedInCustomer(any())).thenReturn(Optional.of(customer));
        when(categoryService.findCategoryByName(anyString())).thenReturn(category);
        when(shoppingCartLineItemService.totalQuantityByShoppingCartID(anyLong())).thenReturn("5");

        mockMvc.perform(get("/categories")
                    .param("categoryName","ABC"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("category"))
                .andExpect(view().name("store/categoryDetails"));

    }
    @Test
    void renderImageFromDatabase() throws Exception {

        Product product = Product.builder().id(1L).sku(1L).build();

        String mockFile = "This is a mock file";

        Byte [] fileByteObject = new Byte[mockFile.getBytes().length];

        int i = 0;

        for (byte primitiveByte : mockFile.getBytes()) {
            fileByteObject[i++] = primitiveByte;
        }

        product.setImage(fileByteObject);
        when(productService.findProductBySku(anyLong())).thenReturn(product);

        MockHttpServletResponse response = mockMvc.perform(get("/categories/products/image")
                    .param("sku","1"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte [] responseByte = response.getContentAsByteArray();

        assertTrue(Arrays.equals(mockFile.getBytes(),responseByte));

    }
    @Test
    void addToCart () throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = Product.builder().sku(1L).productPrice(3d).build();
        ShoppingCartLineItem cartLineItem = new ShoppingCartLineItem();

       when(productService.findProductBySku(anyLong())).thenReturn(product);
       when(shoppingCartService.save(any())).thenReturn(shoppingCart);
       when(shoppingCartLineItemService.save(any())).thenReturn(cartLineItem);

        mockMvc.perform(post("/categories/products")
                    .param("sku","1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/categories/products?sku=1" ));

       verify(shoppingCartLineItemService).save(any());

    }
*/

}