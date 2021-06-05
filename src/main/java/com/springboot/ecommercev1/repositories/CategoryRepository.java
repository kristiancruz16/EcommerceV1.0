package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
