package com.springboot.security.services;

import com.springboot.security.dto.UserDto;
import com.springboot.security.exceptions.UserAlreadyExistsException;
import com.springboot.security.models.User;
import com.springboot.security.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author KMCruz
 * 7/2/2021
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;



    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User registerNewUser(UserDto userDto) {
        if(isEmailExists(userDto.getEmail())){
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setPassword(userDto.getPassword());
        user.setRole("USER");
        return userRepository.save(user);
    }

    private boolean isEmailExists(String email) {
        return userRepository.findByEmail(email)!=null;
    }
/*    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10);
    }*/
}
