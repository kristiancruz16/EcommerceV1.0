package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author KMCruz
 * 6/8/2021
 */
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController controller;

    List<Category> categoryList;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        categoryList = new ArrayList<>();
        categoryList.add(Category.builder().id(1L).build());
        categoryList.add(Category.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    void showAllCategory() throws Exception {
        when(categoryService.findAll()).thenReturn(categoryList);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/allCategories"))
                .andExpect(model().attribute("categories",hasSize(2)));

    }

    @Test
    void showCategoryDetails() throws Exception {
        when(categoryService.findById(anyLong())).thenReturn(Category.builder().id(1L).build());

        mockMvc.perform(get("/categories/145"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/categoryDetails"))
                .andExpect(model().attribute("category",hasProperty("id",is(1L))));
    }

    @Test
    void initializeNewCategoryForm() throws Exception {
        mockMvc.perform(get("/categories/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/createOrUpdateCategoryForm"))
                .andExpect(model().attributeExists("category"));
        verifyNoInteractions(categoryService);
    }

}