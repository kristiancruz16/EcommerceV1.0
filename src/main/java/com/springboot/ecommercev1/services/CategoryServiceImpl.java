package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findByCategoryCode(String categoryCode) {

        return categoryRepository.findByCategoryCode(categoryCode);
    }

    @Override
    public List<Category> findAllByNameLikeIgnoreCase(String name) {

        return categoryRepository.findAllByNameLikeIgnoreCase(name);
    }

    @Override
    public List <Category> findAll() {
        List<Category> categoryList = new ArrayList<>();
        categoryRepository.findAll().forEach(categoryList::add);
        return categoryList;
    }

    @Override
    public Category findById(Long aLong) {
        return categoryRepository.findById(aLong).orElse(null);
    }

    @Override
    public Category save(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);
    }
}
