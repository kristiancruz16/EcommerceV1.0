package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.ShoppingCartLineItem;

/**
 * @author KMCruz
 * 6/14/2021
 */
public interface ShoppingCartLineItemService <T,ID>{

    ShoppingCartLineItem save (T object);

    void delete(T object);

    ShoppingCartLineItem findByID(ID id);
}
