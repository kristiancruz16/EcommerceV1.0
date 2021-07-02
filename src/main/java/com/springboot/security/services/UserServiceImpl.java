package com.springboot.security.services;

import com.springboot.security.dto.UserDto;
import com.springboot.security.exceptions.UserAlreadyExistsException;
import com.springboot.security.models.User;
import com.springboot.security.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.springboot.security.models.Role.*;

/**
 * @author KMCruz
 * 7/2/2021
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(USER);
        return userRepository.save(user);
    }

    @Override
    public User savedRegisteredUser(User user) {
        return userRepository.save(user);
    }

    private boolean isEmailExists(String email) {
        return userRepository.findByEmail(email)!=null;
    }
}
