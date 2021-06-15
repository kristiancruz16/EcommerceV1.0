package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.repositories.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KMCruz
 * 6/14/2021
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<ShoppingCart> findAll() {
        List <ShoppingCart> shoppingCartList = new ArrayList<>();
        shoppingCartRepository.findAll().forEach(shoppingCartList::add);
        return shoppingCartList;
    }

    @Override
    public ShoppingCart findById(String id) {

        return shoppingCartRepository.findById(id).orElse(null);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void delete(ShoppingCart shoppingCart) {
        shoppingCartRepository.delete(shoppingCart);
    }

    @Override
    public void deleteById(String id) {
        shoppingCartRepository.deleteById(id);
    }
}
