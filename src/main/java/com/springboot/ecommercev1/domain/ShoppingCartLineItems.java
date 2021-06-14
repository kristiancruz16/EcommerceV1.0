package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author KMCruz
 * 6/14/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart_line_items")
public class ShoppingCartLineItems extends BaseEntity{

    @Builder
    public ShoppingCartLineItems(Long id, Integer quantity, Double lineAmount, Product product, ShoppingCart shoppingCart) {
        super(id);
        this.quantity = quantity;
        this.lineAmount = lineAmount;
        this.product = product;
        this.shoppingCart = shoppingCart;
    }

    private Integer quantity;

    private Double lineAmount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private ShoppingCart shoppingCart;

}
