package com.springboot.ecommercev1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author KMCruz
 * 7/10/2021
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Payment  extends BaseEntity{

    private Double paymentAmount;

    @OneToOne
    @JoinColumn(name = "customer_order_id")
    private CustomerOrder order;

    @OneToOne
    @JoinColumn(name = "credit_card_info_id")
    private CreditCardInfo creditCardInfo;
}
