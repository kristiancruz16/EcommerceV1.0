package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Category;

import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface CategoryService extends CrudService<Category,Long>{
    Category findByCategoryCode(String categoryCode);

    Set<Category> findAllByNameLikeIgnoreCase(String name);
}
