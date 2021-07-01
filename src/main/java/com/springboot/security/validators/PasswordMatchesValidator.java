package com.springboot.security.validators;

import com.springboot.security.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author KMCruz
 * 7/1/2021
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserDto userDto = (UserDto) obj;
        boolean isEquals = userDto.getPassword().equals(userDto.getMatchingPassword());
        return isEquals;
    }


}
