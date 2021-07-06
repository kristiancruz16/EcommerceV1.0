package com.springboot.security.services;

import com.springboot.security.exceptions.UserAccountBlocked;
import com.springboot.security.models.CustomUser;
import com.springboot.security.models.User;
import com.springboot.security.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


/**
 * @author KMCruz
 * 7/2/2021
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpServletRequest request;
    private final LoginAttemptService loginAttemptService;

    public CustomUserDetailsService(UserRepository userRepository, HttpServletRequest request,
                                    LoginAttemptService loginAttemptService) {
        this.userRepository = userRepository;
        this.request = request;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String clientIp = getClientIp();
        if(loginAttemptService.isIpBlocked(clientIp)){
            throw new UserAccountBlocked("User account is blocked");
        }
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

    public String getClientIp(){
        String xfHeader = request.getHeader("X-Forwarder-For");
        if(xfHeader==null){
            return request.getRemoteAddr();
        }else {
            String clientIp = xfHeader.split(",")[0];
            return clientIp;
        }
    }

}

