package com.springboot.ecommercev1.domain;

import com.springboot.security.models.User;
import lombok.*;

import javax.persistence.*;
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
    public Customer(Long id, String phoneNo,
                    User user, List<Address> address,
                    List<CreditCardInfo> creditCardInfo,
                    List<CustomerOrder> orders) {
        super(id);
        this.phoneNo = phoneNo;
        this.user = user;
        this.address = address;
        this.creditCardInfo = creditCardInfo;
        this.orders = orders;
    }

    private String phoneNo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CreditCardInfo> creditCardInfo;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> orders;
}
