package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    public Product(Long id, String name, Long sku, String productDescription, Double productPrice, Category category) {
        super(id, name);
        this.sku = sku;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.category = category;
    }

    @NotNull(message = "required")
    private Long sku;

    @NotBlank(message = "required")
    private String productDescription;

    @Digits(integer = 5, fraction = 2, message="Invalid input")
    private Double productPrice;

    @Lob
    private Byte [] image;

    @ManyToOne
    @JoinColumn( name = "category_id")
    private Category category;

}
