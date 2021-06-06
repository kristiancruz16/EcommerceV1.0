package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * @author KMCruz
 * 6/5/2021
 */

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    public static final String NAME = "NIKE";

    @Mock
    ProductRepository productRepositoryMock;

    @InjectMocks
    ProductServiceImpl productServiceMock;

    @Captor
    ArgumentCaptor<Product> productArgumentCaptor;

    Product returnProduct;

    @BeforeEach
    void setUp() {
        returnProduct = Product.builder().id(1L).name(NAME).sku(12345678L).build();
    }

    @Test
    void findAll() {
        List<Product>productList = new ArrayList<>();
        productList.add(Product.builder().id(1L).build());
        productList.add(Product.builder().id(2L).build());

        when(productRepositoryMock.findAll()).thenReturn(productList);

        List <Product> products = productServiceMock.findAll();
        assertNotNull(products);
        assertEquals(2,products.size());

    }

    @Test
    void findById() {
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(returnProduct));
        Product product = productServiceMock.findById(1L);
        assertNotNull(product);
    }

    @Test
    void findByIdNotFound() {
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        Product product = productServiceMock.findById(1L);
        assertNull(product);
    }

    @Test
    void save() {
        Product productToSave = Product.builder().id(1L).build();

        productServiceMock.save(productToSave);

        then(productRepositoryMock).should().save(productArgumentCaptor.capture());
        Product productArgumentCaptorValue = productArgumentCaptor.getValue();
        assertEquals(productArgumentCaptorValue,productToSave);

    }

    @Test
    void delete() {
        productServiceMock.delete(returnProduct);
        verify(productRepositoryMock,times(1)).delete(any());
    }

    @Test
    void deleteById() {
        productServiceMock.deleteById(1L);
        verify(productRepositoryMock,times(1)).deleteById(anyLong());
    }

    @Test
    void findAllBySkuLike() {
        when(productRepositoryMock.findAllBySkuLike(anyLong())).thenReturn(Arrays.asList(returnProduct));
        List <Product> productList = productServiceMock.findAllBySkuLike(12L);
        assertEquals(returnProduct,productList.get(0));
        verify(productRepositoryMock).findAllBySkuLike(anyLong());
    }

    @Test
    void findAllByNameLikeIgnoreCase() {
        when(productRepositoryMock.findAllByNameLikeIgnoreCase(any())).thenReturn(Arrays.asList(returnProduct));
        List<Product> productList = productServiceMock.findAllByNameLikeIgnoreCase("ni");
        assertEquals(NAME,productList.get(0).getName());
    }
}