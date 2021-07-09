package com.springboot.ecommercev1.domain;

import com.springboot.security.models.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author KMCruz
 * 7/9/2021
 */
@Data
@Entity
public class Customer extends BaseEntity{

    private String phoneNo;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
