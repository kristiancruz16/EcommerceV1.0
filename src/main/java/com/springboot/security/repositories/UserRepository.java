package com.springboot.security.repositories;

import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 7/1/2021
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail (String email);

    User findUserByVerificationToken(VerificationToken vToken);
}
