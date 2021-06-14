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
@AllArgsConstructor
@Entity
@Table(name = "shopping_cart_line_items")
public class ShoppingCartLineItem {

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
