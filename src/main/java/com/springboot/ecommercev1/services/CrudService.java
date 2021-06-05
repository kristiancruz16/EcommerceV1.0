package com.springboot.ecommercev1.services;

import java.util.Set;

/**
 * @author KMCruz
 * 6/5/2021
 */
public interface CrudService <T,ID>{

    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);

}
