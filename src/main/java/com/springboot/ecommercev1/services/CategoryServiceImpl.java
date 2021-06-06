package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Category;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public Category findByCategoryCode(String categoryCode) {
        return null;
    }

    @Override
    public Set<Category> findAllByNameLikeIgnoreCase(String name) {
        return null;
    }

    @Override
    public Set<Category> findAll() {
        return null;
    }

    @Override
    public Category findById(Long aLong) {
        return null;
    }

    @Override
    public Category save(Category object) {
        return null;
    }

    @Override
    public void delete(Category object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
