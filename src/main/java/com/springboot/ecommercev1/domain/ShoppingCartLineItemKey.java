package com.springboot.ecommercev1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author KMCruz
 * 6/14/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ShoppingCartLineItemKey implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "shopping_cart_id")
    private String shoppingCartId;
}
