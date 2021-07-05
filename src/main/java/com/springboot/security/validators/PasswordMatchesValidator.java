package com.springboot.security.validators;

import com.springboot.security.dto.PasswordDto;
import com.springboot.security.dto.UserDto;
import com.springboot.security.models.User;

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
        boolean isEquals = false;
        if(obj instanceof UserDto) {
            UserDto userDto = (UserDto) obj;
            isEquals = userDto.getPassword().equals(userDto.getMatchingPassword());
        }else if(obj instanceof PasswordDto){
            PasswordDto passwordDto = (PasswordDto) obj;
            isEquals = passwordDto.getPassword().equals(passwordDto.getMatchingPassword());
        }
        return isEquals;
    }


}
