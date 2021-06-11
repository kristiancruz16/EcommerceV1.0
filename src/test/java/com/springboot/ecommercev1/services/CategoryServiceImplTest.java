package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author KMCruz
 * 6/6/2021
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    private final static String CODE = "AAA";
    private final static String NAME = "ALPHA ALPHA ALPHA";

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    Category returnCategory;

    @BeforeEach
    void setUp() {
        returnCategory = Category.builder().id(1L).name(NAME).categoryCode(CODE).build();
    }

    @Test
    void findByCategoryCode() {
        when(categoryRepository.findByCategoryCode(any())).thenReturn(returnCategory);

        Category foundCategory = categoryService.findByCategoryCode(CODE);

        assertEquals(CODE,foundCategory.getCategoryCode());
        verify(categoryRepository).findByCategoryCode(any());
    }

    @Test
    void findAllByNameLikeIgnoreCase() {
        when(categoryRepository.findAllByNameLikeIgnoreCase(any())).thenReturn(Arrays.asList(returnCategory));

        List <Category> foundCategoryList = categoryService.findAllByNameLikeIgnoreCase("alpha");

        assertEquals(NAME,foundCategoryList.get(0).getName());
        verify(categoryRepository).findAllByNameLikeIgnoreCase(any());

    }

    @Test
    void existsByCategoryCode() {
        when(categoryRepository.existsByCategoryCodeIgnoreCase(anyString())).thenReturn(Optional.of(returnCategory).isPresent());

        boolean result = categoryService.existsByCategoryCodeIgnoreCase("ABC");

        assertTrue(result);

    }
    @Test
    void existsByCategoryCodeNotFound() {
        when(categoryRepository.existsByCategoryCodeIgnoreCase(anyString())).thenReturn(Optional.empty().isPresent());

        boolean result = categoryService.existsByCategoryCodeIgnoreCase("ABC");

        assertFalse(result);
    }

    @Test
    void existsByName() {
        when(categoryRepository.existsByNameIgnoreCase(anyString())).thenReturn(Optional.of(returnCategory).isPresent());

        boolean result = categoryService.existsByNameIgnoreCase("Alpha Beta Charlie");

        assertTrue(result);

    }

    @Test
    void existsByNameNotFound () {
        when(categoryRepository.existsByNameIgnoreCase(anyString())).thenReturn(Optional.empty().isPresent());

        boolean result = categoryService.existsByNameIgnoreCase("Alpha Beta Charlie");

        assertFalse(result);
    }

    @Test
    void findAll() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.builder().id(1L).build());
        categoryList.add(Category.builder().id(2L).build());
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List <Category> foundAllCategoryList = categoryService.findAll();

        assertNotNull(foundAllCategoryList);
        assertEquals(2,foundAllCategoryList.size());
    }

    @Test
    void findById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(returnCategory));

        Category foundByIdCategory = categoryService.findById(1L);

        assertNotNull(foundByIdCategory);
    }

    @Test
    void findByIdNotFound () {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Category nullValueCategory = categoryService.findById(1L);

        assertNull(nullValueCategory);
    }


    @Test
    void save() {
        Category categoryToSave = Category.builder().id(1L).build();
        when(categoryRepository.save(any())).thenReturn(returnCategory);

        Category savedCategory = categoryService.save(categoryToSave);

        assertNotNull(savedCategory);
        verify(categoryRepository).save(any());
    }

    @Test
    void delete() {
        categoryService.delete(returnCategory);

        verify(categoryRepository,times(1)).delete(any());
    }

    @Test
    void deleteById() {
        categoryService.deleteById(1L);

        verify(categoryRepository,times(1)).deleteById(anyLong());
    }
}   