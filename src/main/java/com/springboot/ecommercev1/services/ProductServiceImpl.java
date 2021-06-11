package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productList::add);
        return productList;
    }

    @Override
    public Product findById(Long aLong) {
        return productRepository.findById(aLong).orElse(null);

    }

    @Override
    public Product save(Product product) {
    return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteById(Long aLong) {
        productRepository.deleteById(aLong);
    }

    @Override
    public List <Product> findAllBySkuLike(Long sku) {
        return productRepository.findAllBySkuLike(sku);
    }

    @Override
    public Product findBySku(Long sku) {
        return productRepository.findBySku(sku);
    }

    @Override
    public List<Product> findAllByNameLikeIgnoreCase(String name) {
        return productRepository.findAllByNameLikeIgnoreCase(name);
    }
}
