package com.springboot.security.services;

import com.springboot.security.models.PasswordResetToken;
import com.springboot.security.models.User;
import com.springboot.security.repositories.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

/**
 * @author KMCruz
 * 7/4/2021
 */
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public void createPasswordResetToken(User user, String resetToken) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        if(user.hasPasswordResetToken()){
            passwordResetToken = user.getPasswordResetToken();
        }
        passwordResetToken = passwordResetToken.createPasswordResetToken(user, resetToken);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken findPasswordResetTokenByResetToken(String token) {
        return passwordResetTokenRepository.findByResetToken(token);
    }

    @Override
    public void deletePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }
}
