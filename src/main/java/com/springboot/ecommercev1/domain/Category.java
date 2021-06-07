package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity{

    @Builder
    public Category(Long id, String name, String categoryCode, Set<Product> products) {
        super(id, name);
        this.categoryCode = categoryCode;
        this.products = products;
    }

    private String categoryCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Product> products = new HashSet<>();

}
