package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Customer;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.Optional;


/**
 * @author KMCruz
 * 7/10/2021
 */
public interface CustomerService {
    Customer findLoggedInCustomer(String email);
}
