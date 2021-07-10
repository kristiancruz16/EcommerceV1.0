package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 7/10/2021
 */
public interface AddressRepository extends JpaRepository<Address,Long> {
}
