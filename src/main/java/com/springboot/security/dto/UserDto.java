package com.springboot.security.dto;

import com.springboot.security.validators.PasswordMatches;
import com.springboot.security.validators.ValidEmail;
import com.springboot.security.validators.ValidPassword;
import lombok.Data;

/**
 * @author KMCruz
 * 7/1/2021
 */
@Data
@PasswordMatches
public class UserDto {
    private String firstName;
    private String lastName;

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
    private String matchingPassword;
}
