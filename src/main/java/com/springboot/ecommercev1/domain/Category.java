package com.springboot.ecommercev1.domain;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table
@Entity
public class Category extends BaseEntity{

    @Builder
    public Category(Long id, String name, Set<Product> products) {
        super(id);
        this.name = name;
        this.products = products;
    }

    @NotBlank(message = "required")
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Product> products = new HashSet<>();

}
