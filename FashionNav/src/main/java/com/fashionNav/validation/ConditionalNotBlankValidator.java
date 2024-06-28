package com.fashionNav.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 구글 로그인과 일반 사용자를 구분하기 위해서
 * ConditionalNotBlankValidator 클래스는 ConditionalNotBlank 애너테이션의 논리를 구현합니다.
 * 이 클래스는 특정 조건에 따라 필드의 값이 비어 있지 않은지 검증합니다.
 * 조건 필드와 조건 값을 검사하고, 조건이 만족되면 검증 대상 필드가 비어 있지 않은지 확인합니다.
 * 조건이 만족되지 않으면 해당 필드에 대한 검증을 수행하지 않습니다.
 */
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