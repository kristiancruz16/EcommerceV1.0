package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * @author KMCruz
 * 6/5/2021
 */
public interface ProductRepository extends CrudRepository<Product,Long> {

    List<Product> findAllBySkuLike(Long sku);


    List<Product> findAllByNameLikeIgnoreCase(String name);

    Product findProductByName(String name);

    Product findProductBySku(Long sku);

}
