package com.springboot.ecommercev1.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author KMCruz
 * 6/5/2021
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category extends BaseEntity{

    @Builder
    public Category(Long id, String name) {
        super(id, name);
    }

    private String categoryCode;
}
