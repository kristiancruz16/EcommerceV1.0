package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryCode(String categoryCode);

    List<Category> findAllByNameLikeIgnoreCase(String name);

}
