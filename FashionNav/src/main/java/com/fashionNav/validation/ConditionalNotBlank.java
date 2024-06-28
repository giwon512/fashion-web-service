package com.fashionNav.validation;

import com.fashionNav.validation.ConditionalNotBlankValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ConditionalNotBlank 애너테이션은 특정 조건에 따라 필드의 값이 비어 있지 않아야 함을 검증하는 제약 조건을 정의합니다.
 * 이 애너테이션은 필드 또는 타입 수준에서 사용될 수 있으며, 조건 필드의 값이 특정 값과 일치할 때 검증 대상 필드가 비어 있지 않은지 확인합니다.
 * 해당 조건을 검사하기 위해 ConditionalNotBlankValidator 클래스를 사용합니다.
 */
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