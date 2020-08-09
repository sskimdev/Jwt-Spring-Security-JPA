package com.bithumbhomework.member.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bithumbhomework.member.validation.validator.NullOrNotBlankValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
public @interface NullOrNotBlank {
	String message() default "{javax.validation.constraints.Pattern.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}