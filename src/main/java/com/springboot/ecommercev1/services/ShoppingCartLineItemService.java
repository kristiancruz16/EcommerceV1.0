package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author KMCruz
 * 6/14/2021
 */
public interface ShoppingCartLineItemService <T,ID> {

    ShoppingCartLineItem save(T object);

    void delete(T object);

    ShoppingCartLineItem findByID(ID id);

    String totalQuantityByShoppingCartID(String id);
}


