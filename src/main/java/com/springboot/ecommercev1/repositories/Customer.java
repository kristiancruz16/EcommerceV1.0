package com.springboot.ecommercev1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 7/10/2021
 */
public interface Customer extends JpaRepository<Customer,Long> {
}
