package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author KMCruz
 * 6/14/2021
 */
public interface ShoppingCartLineItemRepository extends CrudRepository<ShoppingCartLineItem, ShoppingCartLineItemKey> {

    @Query(value = "SELECT sum(cartItem.quantity) FROM ShoppingCartLineItem  cartItem WHERE cartItem.shoppingCart.id= :shoppingCartID")
    Optional<String> totalQuantityByShoppingCartID(@Param("shoppingCartID") String id);
}
