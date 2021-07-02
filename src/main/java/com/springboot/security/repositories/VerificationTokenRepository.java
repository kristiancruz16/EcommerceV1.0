package com.springboot.security.repositories;

import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 7/2/2021
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByUser(User user);
}
