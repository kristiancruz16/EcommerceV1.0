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
@AllArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity{
    @Builder
    public ShoppingCart(Long id, List<ShoppingCartLineItem> shoppingCartList) {
        super(id);
        this.shoppingCartList = shoppingCartList;
    }

    @OneToMany(mappedBy="shoppingCart")
    private List<ShoppingCartLineItem> shoppingCartList = new ArrayList<>();

}
