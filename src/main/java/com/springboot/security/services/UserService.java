package com.springboot.security.services;

import com.springboot.security.dto.UserDto;
import com.springboot.security.models.User;
import com.springboot.security.models.VerificationToken;

/**
 * @author KMCruz
 * 7/2/2021
 */
public interface UserService {
    User registerNewUser(UserDto userDto);

    User savedRegisteredUser(User user);

    User findUserByVerificationToken(VerificationToken vToken);
}
