package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.CustomerOrder;
import com.springboot.ecommercev1.repositories.CustomerOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KMCruz
 * 7/10/2021
 */
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public List<CustomerOrder> findAll() {
        return null;
    }

    @Override
    public CustomerOrder findById(Long id) {
        return customerOrderRepository.findById(id).orElse(null);
    }

    @Override
    public CustomerOrder save(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public void delete(CustomerOrder object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
