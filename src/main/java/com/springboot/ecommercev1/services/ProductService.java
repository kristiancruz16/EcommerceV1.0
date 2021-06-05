package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;

import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface ProductService extends CrudService<Product,Long>{

    Set<Product> findAllBySkuLikeIgnoreCase(Long sku);

    Set<Product> findAllByNameLikeIgnoreCase(String name);
}
