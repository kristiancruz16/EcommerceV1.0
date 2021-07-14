package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Payment;
import com.springboot.ecommercev1.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KMCruz
 * 7/10/2021
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public Payment findById(Long aLong) {
        return null;
    }

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void delete(Payment object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
