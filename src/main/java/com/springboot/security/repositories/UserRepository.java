package com.springboot.security.repositories;

import com.springboot.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author KMCruz
 * 7/1/2021
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail (String email);
}
