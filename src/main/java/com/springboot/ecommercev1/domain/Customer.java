package com.springboot.ecommercev1.domain;

import com.springboot.security.models.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author KMCruz
 * 7/9/2021
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity{

    @Builder
    public Customer(Long id, String phoneNo, User user, List<Address> address) {
        super(id);
        this.phoneNo = phoneNo;
        this.user = user;
        this.address = address;
    }

    private String phoneNo;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CreditCardInfo> creditCardInfo;
}
