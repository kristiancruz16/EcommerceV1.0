package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author KMCruz
 * 6/14/2021
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart{

    @Builder
    public ShoppingCart(String id, List<ShoppingCartLineItem> shoppingCartList) {
        this.id = id;
        this.shoppingCartList = shoppingCartList;
    }

    @Id
    private String id;

    @OneToMany(mappedBy="shoppingCart")
    private List<ShoppingCartLineItem> shoppingCartList = new ArrayList<>();

    public ShoppingCartLineItem addShoppingCartLineItem(Product product) {

        for(ShoppingCartLineItem cartLineItem : this.getShoppingCartList()){
            if(cartLineItem.getProduct().equals(product)) {
                cartLineItem.setQuantity(cartLineItem.getQuantity()+1);
                cartLineItem.setLineAmount(cartLineItem.getQuantity()*product.getProductPrice());
                return cartLineItem;
            }
        }
        ShoppingCartLineItem shoppingCartLineItem =new ShoppingCartLineItem();
        shoppingCartLineItem.setId(ShoppingCartLineItemKey.builder().shoppingCartId(this.getId()).productId(product.getId()).build());
        shoppingCartLineItem.setShoppingCart(this);
        shoppingCartLineItem.setProduct(product);
        shoppingCartLineItem.setQuantity(1);
        shoppingCartLineItem.setLineAmount(shoppingCartLineItem.getQuantity() * product.getProductPrice());
        this.getShoppingCartList().add(shoppingCartLineItem);
        return shoppingCartLineItem;
    }
}
