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
public class ShoppingCart{

    public ShoppingCart() {}

    public ShoppingCart(String id, List<ShoppingCartLineItem> shoppingCartList,
                        CustomerOrder order, Customer customer) {
        this.id = id;
        this.shoppingCartList = shoppingCartList;
        this.order = order;
        this.customer = customer;
    }

    @Id
    private String id;

    @OneToMany(mappedBy="shoppingCart")
    private List<ShoppingCartLineItem> shoppingCartList = new ArrayList<>();

    @OneToOne(mappedBy = "shoppingCart")
    private CustomerOrder order;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
