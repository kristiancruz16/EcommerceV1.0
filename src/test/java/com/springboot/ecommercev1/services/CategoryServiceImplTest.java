package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    CategoryRepository categoryRepositoryMock;

    @InjectMocks
    CategoryServiceImpl categoryServiceImplMock;

    Category returnCategory;

    @BeforeEach
    void setUp() {
        returnCategory = Category.builder().id(1L).name(NAME).categoryCode(CODE).build();
    }

    @Test
    void findByCategoryCode() {
        when(categoryRepositoryMock.findByCategoryCode(any())).thenReturn(returnCategory);

        Category foundCategory = categoryServiceImplMock.findByCategoryCode(CODE);

        assertEquals(CODE,foundCategory.getCategoryCode());
        verify(categoryRepositoryMock).findByCategoryCode(any());
    }

    @Test
    void findAllByNameLikeIgnoreCase() {
        when(categoryRepositoryMock.findAllByNameLikeIgnoreCase(any())).thenReturn(Arrays.asList(returnCategory));

        List <Category> foundCategoryList = categoryServiceImplMock.findAllByNameLikeIgnoreCase("alpha");

        assertEquals(NAME,foundCategoryList.get(0).getName());
        verify(categoryRepositoryMock).findAllByNameLikeIgnoreCase(any());

    }


    @Test
    void findAll() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.builder().id(1L).build());
        categoryList.add(Category.builder().id(2L).build());
        when(categoryRepositoryMock.findAll()).thenReturn(categoryList);

        List <Category> foundAllCategoryList = categoryServiceImplMock.findAll();

        assertNotNull(foundAllCategoryList);
        assertEquals(2,foundAllCategoryList.size());
    }

    @Test
    void findById() {
        when(categoryRepositoryMock.findById(anyLong())).thenReturn(Optional.of(returnCategory));

        Category foundByIdCategory = categoryServiceImplMock.findById(1L);

        assertNotNull(foundByIdCategory);
    }

    @Test
    void findByIdNotFound () {
        when(categoryRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        Category nullValueCategory = categoryServiceImplMock.findById(1L);

        assertNull(nullValueCategory);
    }


    @Test
    void save() {
        Category categoryToSave = Category.builder().id(1L).build();
        when(categoryRepositoryMock.save(any())).thenReturn(returnCategory);

        Category savedCategory = categoryServiceImplMock.save(categoryToSave);

        assertNotNull(savedCategory);
        verify(categoryRepositoryMock).save(any());
    }

    @Test
    void delete() {
        categoryServiceImplMock.delete(returnCategory);

        verify(categoryRepositoryMock,times(1)).delete(any());
    }

    @Test
    void deleteById() {
        categoryServiceImplMock.deleteById(1L);

        verify(categoryRepositoryMock,times(1)).deleteById(anyLong());
    }
}