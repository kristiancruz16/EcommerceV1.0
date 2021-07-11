package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 7/10/2021
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
