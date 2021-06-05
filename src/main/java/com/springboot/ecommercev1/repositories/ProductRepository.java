package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
    Set<Product> findAllBySkuLikeIgnoreCase(Long sku);

    Set<Product> findAllByNameLikeIgnoreCase(String name);

}
