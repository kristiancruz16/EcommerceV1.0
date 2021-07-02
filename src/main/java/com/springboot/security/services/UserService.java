package com.springboot.security.services;

import com.springboot.security.dto.UserDto;
import com.springboot.security.models.User;

/**
 * @author KMCruz
 * 7/2/2021
 */
public interface UserService {
    User registerNewUser(UserDto userDto);
}
