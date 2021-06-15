package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author KMCruz
 * 6/14/2021
 */
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {

}
