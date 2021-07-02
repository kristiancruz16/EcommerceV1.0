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
        if(hasVToken(user.getVToken())) {
            vToken = user.getVToken();
            vToken = vToken.createOrUpdateVerificationToken(user, regToken);
        } else {
            vToken = vToken.createOrUpdateVerificationToken(user, regToken);
        }
        verificationTokenRepository.save(vToken);

    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    private boolean hasVToken(VerificationToken vToken) {
        return vToken!=null;
    }
}