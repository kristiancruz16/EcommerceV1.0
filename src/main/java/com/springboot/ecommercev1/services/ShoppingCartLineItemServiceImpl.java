package com.springboot.ecommercev1.services;


import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import com.springboot.ecommercev1.repositories.ShoppingCartLineItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author KMCruz
 * 6/14/2021
 */
@Service
public class ShoppingCartLineItemServiceImpl implements ShoppingCartLineItemService<ShoppingCartLineItem,ShoppingCartLineItemKey> {

    private final ShoppingCartLineItemRepository shoppingCartLineItemRepository;

    public ShoppingCartLineItemServiceImpl(ShoppingCartLineItemRepository shoppingCartLineItemRepository) {
        this.shoppingCartLineItemRepository = shoppingCartLineItemRepository;
    }


    @Override
    public ShoppingCartLineItem save(ShoppingCartLineItem shoppingCartLineItem) {
        return shoppingCartLineItemRepository.save(shoppingCartLineItem);
    }

    @Override
    public void delete(ShoppingCartLineItem shoppingCartLineItem) {
        shoppingCartLineItemRepository.delete(shoppingCartLineItem);
    }

    @Override
    public ShoppingCartLineItem findByID(ShoppingCartLineItemKey shoppingCartLineItemKey) {
        return shoppingCartLineItemRepository.findById(shoppingCartLineItemKey).orElse(null);
    }


}
