package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author KMCruz
 * 6/14/2021
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart_line_items")
public class ShoppingCartLineItem{

    @Builder
    public ShoppingCartLineItem(ShoppingCartLineItemKey id, Product product,
                                ShoppingCart shoppingCart, Integer quantity,
                                Double lineAmount) {
        this.id = id;
        this.product = product;
        this.shoppingCart = shoppingCart;
        this.quantity = quantity;
        this.lineAmount = lineAmount;
    }

    @EmbeddedId
    private ShoppingCartLineItemKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("shoppingCartId")
    @JoinColumn
    private ShoppingCart shoppingCart;

    private Integer quantity;

    private Double lineAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartLineItem)) return false;

        ShoppingCartLineItem that = (ShoppingCartLineItem) o;

        if (!id.getProductId().equals(that.getId().getProductId())) return  false;
        return id.getShoppingCartId().equals(that.id.getShoppingCartId());
    }

    public ShoppingCartLineItem addCartLineItem() {

        ShoppingCart currentShoppingCart = this.getShoppingCart();
        List<ShoppingCartLineItem> currentCartLineItems = currentShoppingCart.getShoppingCartList();
        Integer lineItemQuantity = 0;

        for(ShoppingCartLineItem lineItemDetail : currentCartLineItems) {
            if(lineItemDetail.equals(this)) {
                lineItemQuantity = lineItemDetail.getQuantity();
            }
        }

        Integer newLineItemQuantity = ++lineItemQuantity;
        double lineAmount = this.getProduct().getProductPrice() * newLineItemQuantity;

        this.setQuantity(newLineItemQuantity);
        this.setLineAmount(lineAmount);

        return this;
    }

}
