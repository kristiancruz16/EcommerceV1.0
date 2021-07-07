package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface CategoryRepository extends CrudRepository<Category,Long> {

    List<Category> findAllByNameLikeIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    Category findCategoryByName(String name);

}
