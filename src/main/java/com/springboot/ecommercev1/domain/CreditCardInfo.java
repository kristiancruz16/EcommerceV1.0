package com.springboot.ecommercev1.domain;

import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author KMCruz
 * 7/9/2021
 */
@Data
@Entity
public class CreditCardInfo extends BaseEntity {

    public CreditCardInfo() {
    }

    @Builder
    public CreditCardInfo(Long id, CardType cardType,
                          String cardNo, String expiryDate,
                          String cvv, Payment payment) {
        super(id);
        this.cardType = cardType;
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.payment = payment;
    }

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @NotNull
    @NotBlank
    @CreditCardNumber
    private String cardNo;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([0-9][0-9])$")
    private String expiryDate;

    @NotNull
    @NotBlank
    @Digits(integer = 3,fraction = 0)
    private String cvv;

    @OneToOne(mappedBy = "creditCardInfo")
    private Payment payment;


}
