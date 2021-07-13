package com.springboot.ecommercev1.services;


import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import com.springboot.ecommercev1.repositories.ShoppingCartLineItemRepository;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author KMCruz
 * 6/14/2021
 */
@Service
public class ShoppingCartLineItemServiceImpl implements ShoppingCartLineItemService<ShoppingCartLineItem, ShoppingCartLineItemKey> {

    private final ShoppingCartLineItemRepository shoppingCartLineItemRepository;

    public ShoppingCartLineItemServiceImpl(ShoppingCartLineItemRepository shoppingCartLineItemRepository) {
        this.shoppingCartLineItemRepository = shoppingCartLineItemRepository;
    }


    @Override
    public ShoppingCartLineItem save(ShoppingCartLineItem cartLineItem) {
        return shoppingCartLineItemRepository.save(cartLineItem);

    }

    @Override
    public void delete(ShoppingCartLineItem shoppingCartLineItem) {
        shoppingCartLineItemRepository.delete(shoppingCartLineItem);
    }

    @Override
    public ShoppingCartLineItem findByID(ShoppingCartLineItemKey shoppingCartLineItemKey) {
        return shoppingCartLineItemRepository.findById(shoppingCartLineItemKey).orElse(null);
    }

    @Override
    public String totalQuantityByShoppingCartID(Long id) {
        return shoppingCartLineItemRepository.totalQuantityByShoppingCartID(id).orElse("");
    }


}
