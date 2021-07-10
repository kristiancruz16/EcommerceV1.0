package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author KMCruz
 * 7/9/2021
 */
@Data
@Entity
public class CustomerOrder extends BaseEntity {

 public CustomerOrder(){}

@Builder
 public CustomerOrder(Long id, OrderStatus orderStatus,
                      Double orderAmount, Customer customer,
                      Address address, ShoppingCart shoppingCart,
                      Payment payment) {
  super(id);
  this.orderStatus = orderStatus;
  this.orderAmount = orderAmount;
  this.customer = customer;
  this.address = address;
  this.shoppingCart = shoppingCart;
  this.payment = payment;
 }

 @Enumerated(EnumType.STRING)
 private OrderStatus orderStatus;

 private Double orderAmount;

 @ManyToOne
 @JoinColumn(name = "customer_id")
 private Customer customer;

 @OneToOne
 @JoinColumn(name = "address_id")
 private Address address;

 @OneToOne
 @JoinColumn(name = "shopping_cart_id")
 private ShoppingCart shoppingCart;

 @OneToOne(mappedBy = "order")
 private Payment payment;

}
