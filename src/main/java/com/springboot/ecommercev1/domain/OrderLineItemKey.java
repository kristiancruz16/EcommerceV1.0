package com.springboot.ecommercev1.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author KMCruz
 * 7/11/2021
 */
@Data
@Embeddable
public class OrderLineItemKey implements Serializable {

    public OrderLineItemKey() {
    }

    @Builder
    public OrderLineItemKey(Long productId, Long customerOrderId) {
        this.productId = productId;
        this.customerOrderId = customerOrderId;
    }

    private Long productId;

    private Long customerOrderId;

}
