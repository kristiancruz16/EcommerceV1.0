package com.springboot.security.services;

import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;
import com.springboot.security.repositories.VerificationTokenRepository;
import org.springframework.stereotype.Service;

/**
 * @author KMCruz
 * 7/2/2021
 */
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public void createVerificationToken(User user, String regToken) {
        VerificationToken vToken = new VerificationToken();
        if(hasVToken(user.getVerificationToken())) {
            vToken = user.getVerificationToken();
        }
        vToken = vToken.createOrUpdateVerificationToken(user, regToken);
        verificationTokenRepository.save(vToken);

    }

    @Override
    public VerificationToken findVerificationTokenByRegistrationToken(String token) {
        return verificationTokenRepository.findVerificationTokenByRegistrationToken(token);
    }

    @Override
    public VerificationToken findVerificationTokenByUser(User user) {
        return verificationTokenRepository.findVerificationTokenByUser(user);
    }

    @Override
    public void deleteVerificationToken(VerificationToken vToken) {
        verificationTokenRepository.delete(vToken);
    }


    private boolean hasVToken(VerificationToken vToken) {
        return vToken!=null;
    }
}
