package com.springboot.ecommercev1.domain;

import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author KMCruz
 * 7/10/2021
 */
@Data
@Entity
public class Payment  extends BaseEntity{

    public Payment() {}

    @Builder
    public Payment(Long id, Double paymentAmount, CustomerOrder order, CreditCardInfo creditCardInfo) {
        super(id);
        this.paymentAmount = paymentAmount;
        this.order = order;
        this.creditCardInfo = creditCardInfo;
    }

    private Double paymentAmount = 0.00D;

    @OneToOne
    @JoinColumn(name = "customer_order_id")
    private CustomerOrder order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_info_id")
    private CreditCardInfo creditCardInfo;
}
