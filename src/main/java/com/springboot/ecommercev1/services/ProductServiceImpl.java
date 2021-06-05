package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Set<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(Long aLong) {
        return null;
    }

    @Override
    public Product save(Product object) {
        return null;
    }

    @Override
    public void delete(Product object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Set<Product> findAllBySkuLikeIgnoreCase(Long sku) {
        return null;
    }

    @Override
    public Set<Product> findAllByNameLikeIgnoreCase(String name) {
        return null;
    }
}
