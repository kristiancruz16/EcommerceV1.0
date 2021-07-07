package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Category;

import java.util.List;
import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface CategoryService extends CrudService<Category,Long>{

    List<Category> findAllByNameLikeIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    Category findCategoryByName(String name);
}
