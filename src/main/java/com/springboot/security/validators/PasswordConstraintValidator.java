package com.springboot.security.validators;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author KMCruz
 * 7/1/2021
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword,String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new SpecialCharacterRule(1),
                new QwertySequenceRule(3,false),
                new DigitCharacterRule(1),
                new NumericalSequenceRule(3,false),
                new AlphabeticalSequenceRule(3, false),
                new WhitespaceRule()
                ));

        RuleResult ruleResult = validator.validate(new PasswordData(password));
        if (ruleResult.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        validator.getMessages(ruleResult)
                .forEach(e->context
                        .buildConstraintViolationWithTemplate(e)
                        .addConstraintViolation());
        return false;
    }
}
