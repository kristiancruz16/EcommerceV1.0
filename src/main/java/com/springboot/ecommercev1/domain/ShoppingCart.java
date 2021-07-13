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

@Data
@Entity
@Table
public class ShoppingCart extends BaseEntity{

    public ShoppingCart() {}

    @Builder
    public ShoppingCart(Long id, List<ShoppingCartLineItem> shoppingCartList) {
        super(id);
        this.shoppingCartList = shoppingCartList;
    }

    @OneToMany(mappedBy="shoppingCart")
    private List<ShoppingCartLineItem> shoppingCartList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
