package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author KMCruz
 * 6/14/2021
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart_line_items")
public class ShoppingCartLineItem {

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

}
