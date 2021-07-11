package com.springboot.ecommercev1.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author KMCruz
 * 7/11/2021
 */
@Data
@Entity
public class OrderLineItem {

    @EmbeddedId
    private OrderLineItemKey id;

    @ManyToOne
    @MapsId("customerOrderId")
    @JoinColumn(name = "customer_order_id")
    private CustomerOrder customerOrder;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private Double price;

    private Double lineAmount;
}
