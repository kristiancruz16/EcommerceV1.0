package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.services.ImageService;
import com.springboot.ecommercev1.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author KMCruz
 * 6/10/2021
 */
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ProductService productService;

    @Mock
    ImageService imageService;

    @InjectMocks
    ImageController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showUploadImageForm() throws Exception {
        when(productService.findById(anyLong())).thenReturn(Product.builder().id(1L).build());

        mockMvc.perform(get("/admin/categories/1/products/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("images/uploadImage"))
                .andExpect(model().attributeExists("product"));

        verify(productService,times(1)).findById(anyLong());

    }

    @Test
    void processUploadImageForm() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile","image.jpg","image/jpg",
                "image".getBytes());

        mockMvc.perform(multipart("/admin/categories/1/products/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/1/products/1"));

        verify(imageService,times(1)).saveImageFile(anyLong(),any());
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

        MockHttpServletResponse response = mockMvc.perform(get("/admin/categories/1/products/1/productImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte [] responseByte = response.getContentAsByteArray();

        assertTrue(Arrays.equals(mockFile.getBytes(),responseByte));

    }
}