package com.springboot.security.dto;

import com.springboot.security.validators.PasswordMatches;
import com.springboot.security.validators.ValidPassword;
import lombok.Data;
import lombok.Getter;

/**
 * @author KMCruz
 * 7/5/2021
 */
@Data
@PasswordMatches
public class PasswordDto {

    @ValidPassword
    private String password;

    private String matchingPassword;
}
