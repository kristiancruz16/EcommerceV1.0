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
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private MultiValueMap paramsMultiValueMap;

    @BeforeEach
    void setUp() {
        categoryList = new ArrayList<>();
        categoryList.add(Category.builder().id(1L).build());
        categoryList.add(Category.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Map<String,List<String>>  paramsMap = new HashMap<>();
        paramsMap.put("name",List.of("Alpha Beta Charlie"));
        paramsMap.put("categoryCode",List.of("ABC"));

        paramsMultiValueMap = new MultiValueMapAdapter<>(paramsMap);
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

    @Test
    void processNewCategoryForm() throws Exception {
        when(categoryService.save(any())).thenReturn(Category.builder().id(1L).build());

        mockMvc.perform(post("/categories/new").params(paramsMultiValueMap))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/1"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    void initializeUpdateCategoryForm () throws Exception {
        when(categoryService.findById(anyLong())).thenReturn(Category.builder().id(1L).build());

        mockMvc.perform(get("/categories/145/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/createOrUpdateCategoryForm"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    void processUpdateCategoryForm() throws Exception {
        when(categoryService.save(any())).thenReturn(Category.builder().id(1L).name("Alpha Beta").categoryCode("ABC").build());

        mockMvc.perform(post("/categories/1/edit").params(paramsMultiValueMap))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/1"))
                .andExpect(model().attributeExists("category"));

        verify(categoryService).save(any());
    }

    @Test
    void updateReturnErrorInNameWithBlankValue () throws Exception {
        mockMvc.perform(post("/categories/1/edit").param("name",""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("category"))
                .andExpect(model().attributeHasFieldErrors("category","name"))
                .andExpect(view().name("categories/createOrUpdateCategoryForm"));

    }

    @Test
    void updateReturnErrorInCategoryCodeWithBlankValue () throws Exception {
        mockMvc.perform(post("/categories/1/edit").param("categoryCode",""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("category"))
                .andExpect(model().attributeHasFieldErrors("category","categoryCode"))
                .andExpect(view().name("categories/createOrUpdateCategoryForm"));
    }

    @Test
    void updateReturnErrorInCategoryCodeWithValueLessThanMin () throws Exception {
        mockMvc.perform(post("/categories/1/edit").param("categoryCode","AB"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("category"))
                .andExpect(model().attributeHasFieldErrors("category","categoryCode"))
                .andExpect(view().name("categories/createOrUpdateCategoryForm"));
    }

    @Test
    void updateReturnErrorInCategoryCodeWithValueGreaterThanMax () throws Exception {
        mockMvc.perform(post("/categories/1/edit").param("categoryCode","ABCDEF"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("category"))
                .andExpect(model().attributeHasFieldErrors("category","categoryCode"))
                .andExpect(view().name("categories/createOrUpdateCategoryForm"));
    }

}