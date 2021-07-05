package com.springboot.security.repositories;

import com.springboot.security.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 7/4/2021
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByResetToken(String resetToken);
}
