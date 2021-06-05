package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.Entity;

/**
 * @author KMCruz
 * 6/5/2021
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product extends BaseEntity{

    @Builder
    public Product(Long id, String name) {
        super(id, name);
    }

    private Long sku;
    private String description;
    private Integer price;

}
