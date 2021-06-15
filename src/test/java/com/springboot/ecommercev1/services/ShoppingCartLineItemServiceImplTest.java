package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import com.springboot.ecommercev1.repositories.ShoppingCartLineItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author KMCruz
 * 6/15/2021
 */
@ExtendWith(MockitoExtension.class)
class ShoppingCartLineItemServiceImplTest {

    @Mock
    ShoppingCartLineItemRepository shoppingCartLineItemRepository;

    @InjectMocks
    ShoppingCartLineItemServiceImpl shoppingCartLineItemService;

    ShoppingCartLineItem returnShoppingCartLineItem;

    @BeforeEach
    void setUp() {
        returnShoppingCartLineItem = new ShoppingCartLineItem();
    }

    @Test
    void save() {
        when(shoppingCartLineItemRepository.save(any())).thenReturn(returnShoppingCartLineItem);

        ShoppingCartLineItem savedShoppingCartLineItem = shoppingCartLineItemService.save(returnShoppingCartLineItem);

        assertNotNull(savedShoppingCartLineItem);
        verify(shoppingCartLineItemRepository).save(any());
    }

    @Test
    void delete() {
        shoppingCartLineItemService.delete(returnShoppingCartLineItem);
        verify(shoppingCartLineItemRepository,times(1)).delete(any());
    }
}