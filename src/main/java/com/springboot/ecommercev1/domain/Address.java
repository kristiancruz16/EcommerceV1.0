package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author KMCruz
 * 7/9/2021
 */
@Data
@Entity
public class Address extends BaseEntity{

    public Address() {
    }

    @Builder
    public Address(Long id, String addressLine,
                   String baranggay, String city,
                   String province, String postalCode,
                   AddressCategory addressCategory, Customer customer,
                   CustomerOrder order) {
        super(id);
        this.addressLine = addressLine;
        this.baranggay = baranggay;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.addressCategory = addressCategory;
        this.customer = customer;
        this.order = order;
    }

    @NotNull
    @NotBlank
    private String addressLine;

    @NotNull
    @NotBlank
    private String baranggay;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String province;

    @NotNull
    @NotBlank
    @Digits(integer = 4, fraction = 0)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    private AddressCategory addressCategory;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "address")
    private CustomerOrder order;

}
