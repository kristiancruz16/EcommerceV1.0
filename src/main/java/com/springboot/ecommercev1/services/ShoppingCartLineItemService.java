package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;

/**
 * @author KMCruz
 * 6/14/2021
 */
public interface ShoppingCartLineItemService <T,ID>{

    T save (T object);

    void delete(T object);
}
