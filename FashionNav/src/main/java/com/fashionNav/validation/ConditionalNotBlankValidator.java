package com.fashionNav.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class ConditionalNotBlankValidator implements ConstraintValidator<ConditionalNotBlank, Object> {
    private String conditionField;
    private String conditionValue;
    private String validatedField;

    @Override
    public void initialize(ConditionalNotBlank constraintAnnotation) {
        this.conditionField = constraintAnnotation.conditionField();
        this.conditionValue = constraintAnnotation.conditionValue();
        this.validatedField = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);

        try {
            Object conditionFieldValue = beanWrapper.getPropertyValue(conditionField);
            if (conditionFieldValue != null && conditionFieldValue.toString().equals(conditionValue)) {
                return true; // 조건이 만족되면 해당 필드를 검증하지 않음
            }

            Object fieldValue = beanWrapper.getPropertyValue(validatedField);
            return fieldValue != null && !fieldValue.toString().isEmpty();
        } catch (Exception e) {
            // 속성을 찾을 수 없는 경우 로그를 남기고 유효하다고 처리
            System.err.println("Property access error: " + e.getMessage());
            return true;
        }
    }
}