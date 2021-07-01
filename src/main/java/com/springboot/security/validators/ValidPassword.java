package com.springboot.security.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * @author KMCruz
 * 7/1/2021
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Documented
public @interface ValidPassword {
    String message () default "Invalid Password";
    Class<?>[]groups () default {};
    Class<? extends Payload> [] payload () default {};
}
