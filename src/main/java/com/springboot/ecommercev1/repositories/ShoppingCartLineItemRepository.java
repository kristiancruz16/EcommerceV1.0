package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import org.springframework.data.repository.CrudRepository;

/**
 * @author KMCruz
 * 6/14/2021
 */
public interface ShoppingCartLineItemRepository extends CrudRepository<ShoppingCartLineItem, ShoppingCartLineItemKey> {
}
