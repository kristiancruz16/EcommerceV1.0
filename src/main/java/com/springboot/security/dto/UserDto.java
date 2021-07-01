package com.springboot.security.dto;

import lombok.Data;

/**
 * @author KMCruz
 * 7/1/2021
 */
@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String matchingPassword;
}
