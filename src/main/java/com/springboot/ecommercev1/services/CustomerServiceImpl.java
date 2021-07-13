package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Customer;
import com.springboot.ecommercev1.repositories.CustomerRepository;
import com.springboot.security.models.User;
import com.springboot.security.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

/**
 * @author KMCruz
 * 7/10/2021
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Customer findLoggedInCustomer(String email) {
        User user = userRepository.findByEmail(email);
        return customerRepository.findCustomerByUser(user);
    }
}
