package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author KMCruz
 * 7/9/2021
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Address extends BaseEntity{

    @Builder
    public Address(Long id, String addressLine1,
                   String addressLine2, String baranggay,
                   String city, String province, String postalCode,
                   AddressCategory addressCategory, Customer customer) {
        super(id);
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.baranggay = baranggay;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.addressCategory = addressCategory;
        this.customer = customer;
    }

    private String addressLine1;

    private String addressLine2;

    private String baranggay;

    private String city;

    private String province;

    private String postalCode;

    @Enumerated(EnumType.STRING)
    private AddressCategory addressCategory;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;
}
