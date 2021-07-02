package com.springboot.security.services;

import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;

/**
 * @author KMCruz
 * 7/2/2021
 */
public interface VerificationTokenService {
    void createVerificationToken(User user, String token);

    VerificationToken findVerificationTokenByRegistrationToken(String token);

}
