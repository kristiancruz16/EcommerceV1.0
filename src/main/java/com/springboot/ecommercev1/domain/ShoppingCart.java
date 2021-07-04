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
@Table
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

}
