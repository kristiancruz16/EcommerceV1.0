package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public boolean existsProductBySku(Product product) {
        Product foundProduct = productRepository.findProductBySku(product.getSku());
        if(foundProduct!=null) {
            return foundProduct.getId() != product.getId();
        }
        return false;
    }

    @Override
    public List<Product> findAllByNameLikeIgnoreCase(String name) {
        return productRepository.findAllByNameLikeIgnoreCase(name);
    }

    @Override
    public Product findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public Product findProductBySku(Long sku) {
        return  productRepository.findProductBySku(sku);
    }


}
