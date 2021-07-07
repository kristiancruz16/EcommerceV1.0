package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;

import java.util.List;
import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface ProductService extends CrudService<Product,Long>{

    List<Product> findAllBySkuLike(Long sku);

    boolean existsProductBySku(Product product);

    List <Product> findAllByNameLikeIgnoreCase(String name);

    Product findProductByName(String name);
}
