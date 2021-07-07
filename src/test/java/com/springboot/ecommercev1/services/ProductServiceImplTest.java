package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.repositories.ProductRepository;
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
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Captor
    ArgumentCaptor<Product> productArgumentCaptor;

    Product returnProduct;

    @BeforeEach
    void setUp() {
        returnProduct = Product.builder().id(1L).sku(1234L).name(NAME).sku(12345678L).build();
    }

    @Test
    void findAll() {
        List<Product>productList = new ArrayList<>();
        productList.add(Product.builder().id(1L).build());
        productList.add(Product.builder().id(2L).build());

        when(productRepository.findAll()).thenReturn(productList);

        List <Product> products = productService.findAll();
        assertNotNull(products);
        assertEquals(2,products.size());

    }

    @Test
    void findById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(returnProduct));
        Product product = productService.findById(1L);
        assertNotNull(product);
    }

    @Test
    void findByIdNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        Product product = productService.findById(1L);
        assertNull(product);
    }

    @Test
    void save() {
        Product productToSave = Product.builder().id(1L).build();

        productService.save(productToSave);

        then(productRepository).should().save(productArgumentCaptor.capture());
        Product productArgumentCaptorValue = productArgumentCaptor.getValue();
        assertEquals(productArgumentCaptorValue,productToSave);

    }

    @Test
    void delete() {
        productService.delete(returnProduct);
        verify(productRepository,times(1)).delete(any());
    }

    @Test
    void deleteById() {
        productService.deleteById(1L);
        verify(productRepository,times(1)).deleteById(anyLong());
    }

    @Test
    void findAllBySkuLike() {
        when(productRepository.findAllBySkuLike(anyLong())).thenReturn(List.of(returnProduct));
        List <Product> productList = productService.findAllBySkuLike(12L);
        assertEquals(returnProduct,productList.get(0));
        verify(productRepository).findAllBySkuLike(anyLong());
    }

    @Test
    void checkProductWithSameSkuReturnTrue () {
        when(productRepository.findProductBySku(any())).thenReturn(returnProduct);
        Product product = Product.builder().id(2L).sku(1234L).build();
        boolean result = productService.existsProductBySku(product);
        assertTrue(result);
    }


    @Test
    void checkProductWithSameSkuReturnFalse () {
        when(productRepository.findProductBySku(any())).thenReturn(returnProduct);
        Product product = Product.builder().id(1L).sku(4567L).build();
        boolean result = productService.existsProductBySku(product);
        assertFalse(result);
    }
    @Test
    void checkProductWithSameSkuReturnNull () {
        when(productRepository.findProductBySku(any())).thenReturn(null);
        Product product = Product.builder().id(1L).sku(4567L).build();
        boolean result = productService.existsProductBySku(product);
        assertFalse(result);
    }


    @Test
    void findAllByNameLikeIgnoreCase() {
        when(productRepository.findAllByNameLikeIgnoreCase(any())).thenReturn(Arrays.asList(returnProduct));
        List<Product> productList = productService.findAllByNameLikeIgnoreCase("ni");
        assertEquals(NAME,productList.get(0).getName());
    }
}