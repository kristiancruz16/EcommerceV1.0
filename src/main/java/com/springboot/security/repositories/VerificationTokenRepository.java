package com.springboot.security.repositories;

import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author KMCruz
 * 7/2/2021
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findVerificationTokenByRegistrationToken(String token);

    VerificationToken findVerificationTokenByUser(User user);

    List<VerificationToken> findAllByExpiryDateIsLessThanEqual(Date now);
}
