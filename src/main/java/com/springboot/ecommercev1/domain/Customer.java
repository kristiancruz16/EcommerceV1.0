package com.springboot.ecommercev1.domain;

import com.springboot.security.models.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KMCruz
 * 7/9/2021
 */
@Data
@Entity
public class Customer extends BaseEntity{

    public Customer () {}

    @Builder
    public Customer(Long id, String phoneNo, User user, List<CustomerOrder> orders, ShoppingCart shoppingCart) {
        super(id);
        this.phoneNo = phoneNo;
        this.user = user;
        this.orders = orders;
        this.shoppingCart = shoppingCart;
    }

    private String phoneNo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> orders;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;
}
