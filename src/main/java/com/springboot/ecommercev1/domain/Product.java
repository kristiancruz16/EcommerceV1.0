package com.springboot.ecommercev1.domain;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KMCruz
 * 6/5/2021
 */

@Data
@Entity
@Table
public class Product extends BaseEntity{

    public Product() {}

    @Builder
    public Product(Long id, String name, Long sku, String productDescription, Double productPrice, Category category,
                   List<ShoppingCartLineItem> shoppingCartLineItems) {
        super(id);
        this.sku = sku;
        this.name = name;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.category = category;
        this.shoppingCartLineItems = shoppingCartLineItems;
    }


    @NotBlank(message = "required")
    private String name;

    @Column(unique = true)
    @NotNull(message = "required")
    @Min(value = 1)
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

    @OneToMany(mappedBy = "product")
    private List<ShoppingCartLineItem> shoppingCartLineItems = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderLineItem> orderItems;
}
