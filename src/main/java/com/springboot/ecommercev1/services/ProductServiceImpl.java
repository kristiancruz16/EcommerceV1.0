package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.repositories.ProductRepository;
import org.springframework.stereotype.Service;

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
    public Set<Product> findAll() {
        Set <Product> products = new HashSet<>();
        productRepository.findAll()
                .forEach(products::add);
        return products;
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
    public List<Product> findAllByNameLikeIgnoreCase(String name) {
        return productRepository.findAllByNameLikeIgnoreCase(name);
    }
}
