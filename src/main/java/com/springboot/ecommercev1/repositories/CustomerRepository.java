package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.Customer;
import com.springboot.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author KMCruz
 * 7/10/2021
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findCustomerByUser(User user);
}
