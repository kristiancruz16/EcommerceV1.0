package com.springboot.security.services;

import com.springboot.security.models.PasswordResetToken;
import com.springboot.security.models.User;

/**
 * @author KMCruz
 * 7/4/2021
 */
public interface PasswordResetTokenService {
    void createPasswordResetToken(User user, String resetToken);

    PasswordResetToken findPasswordResetTokenByResetToken(String token);

    void deletePasswordResetToken(PasswordResetToken passwordResetToken);
}
