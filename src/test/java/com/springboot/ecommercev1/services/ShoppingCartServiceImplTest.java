package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.repositories.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author KMCruz
 * 6/15/2021
 */
@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    ShoppingCartServiceImpl shoppingCartService;

    ShoppingCart returnShoppingCart;

    @BeforeEach
    void setUp() {
        returnShoppingCart =ShoppingCart.builder().id(1L).build();
    }

    @Test
    void findAll() {
        when(shoppingCartRepository.findAll()).thenReturn(List.of(returnShoppingCart));

        List <ShoppingCart> shoppingCartList = shoppingCartService.findAll();

        assertNotNull(shoppingCartList);
        assertEquals(1,shoppingCartList.size());
    }

    @Test
    void findById() {
        when(shoppingCartRepository.findById(anyString())).thenReturn(Optional.of(returnShoppingCart));

        ShoppingCart  shoppingCart = shoppingCartService.findById("1L");

        assertNotNull(shoppingCart);
    }

    @Test
    void findByIdNotFound () {
        when(shoppingCartRepository.findById(anyString())).thenReturn(Optional.empty());

        ShoppingCart shoppingCart = shoppingCartService.findById("1L");

        assertNull(shoppingCart);
    }

    @Test
    void save() {
        when(shoppingCartRepository.save(any())).thenReturn(returnShoppingCart);

        ShoppingCart savedShoppingCart = shoppingCartService.save(returnShoppingCart);

        assertNotNull(savedShoppingCart);
        verify(shoppingCartRepository).save(any());
    }

    @Test
    void delete() {
        shoppingCartService.delete(returnShoppingCart);

        verify(shoppingCartRepository,times(1)).delete(any());
    }

    @Test
    void deleteById() {
        shoppingCartService.deleteById("1L");

        verify(shoppingCartRepository,times(1)).deleteById(anyString());
    }
}