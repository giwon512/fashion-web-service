package com.fashionNav.validation;

import com.fashionNav.validation.ConditionalNotBlankValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionalNotBlankValidator.class)
public @interface ConditionalNotBlank {
    String message() default "Field cannot be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String field();
    String conditionField();
    String conditionValue();
}