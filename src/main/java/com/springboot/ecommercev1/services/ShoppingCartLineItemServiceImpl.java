package com.springboot.ecommercev1.services;


import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import com.springboot.ecommercev1.repositories.ShoppingCartLineItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
    public ShoppingCartLineItem save(ShoppingCartLineItem cartLineItem) {

        ShoppingCart currentShoppingCart = cartLineItem.getShoppingCart();
        List<ShoppingCartLineItem> currentCartListDetails = currentShoppingCart.getShoppingCartList();

        for(ShoppingCartLineItem lineItemDetail : currentCartListDetails) {
            if(lineItemDetail.equals(cartLineItem)) {
                int getLineItemQuantity = lineItemDetail.getQuantity();
                int addedItemQuantity = ++getLineItemQuantity;
                double productPrice =  lineItemDetail.getProduct().getProductPrice();
                double lineAmountTotal = addedItemQuantity * productPrice;

                cartLineItem.setQuantity(addedItemQuantity);
                cartLineItem.setLineAmount(lineAmountTotal);
                return shoppingCartLineItemRepository.save(cartLineItem);
            }
        }

        cartLineItem.setQuantity(1);
        cartLineItem.setLineAmount(cartLineItem.getQuantity()*cartLineItem.getProduct().getProductPrice());
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


}
