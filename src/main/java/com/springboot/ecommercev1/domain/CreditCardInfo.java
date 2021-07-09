package com.springboot.ecommercev1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

/**
 * @author KMCruz
 * 7/9/2021
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class CreditCardInfo extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @CreditCardNumber
    private String cardNo;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([0-9][0-9])$")
    private String expiryDate;

    @Digits(integer = 3,fraction = 0)
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;
}
