package com.springboot.ecommercev1.repositories;

import com.springboot.ecommercev1.domain.CreditCardInfo;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 7/10/2021
 */
public interface CreditCardInfoRepository extends JpaRepository<CreditCardInfo,Long> {
}
