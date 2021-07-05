package com.springboot.security.services;

import com.springboot.security.models.CustomUser;
import com.springboot.security.models.User;
import com.springboot.security.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




/**
 * @author KMCruz
 * 7/2/2021
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try{
            User user = userRepository.findByEmail(email);
            if(user==null){
                throw new UsernameNotFoundException("Username does not exists");
            }
            return new CustomUser(user);
        }catch (UsernameNotFoundException usernameNotFoundException){
            throw new UsernameNotFoundException("Username does not exists");
        }
    }
}

