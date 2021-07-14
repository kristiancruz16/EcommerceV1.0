package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
                      Customer customer, Address address,
                      Payment payment) {
  super(id);
  this.orderStatus = orderStatus;
  this.customer = customer;
  this.address = address;
  this.payment = payment;
 }

 @Enumerated(EnumType.STRING)
 private OrderStatus orderStatus;

 @ManyToOne
 @JoinColumn(name = "customer_id")
 private Customer customer;

 @OneToOne(cascade = CascadeType.ALL)
 @JoinColumn(name = "address_id")
 private Address address;

 @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
 private Payment payment;

 @OneToMany(mappedBy = "customerOrder",cascade = CascadeType.ALL)
 private List<OrderLineItem> orderItems;

}
