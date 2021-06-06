package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author KMCruz
 * 6/5/2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity{

    @Builder
    public Product(Long id, String name, Long sku, String productDescription, Integer productPrice, Category category) {
        super(id, name);
        this.sku = sku;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.category = category;
    }

    private Long sku;
    private String productDescription;
    private Integer productPrice;

    @ManyToOne
    @JoinColumn( name = "product_id")
    private Category category;

}
