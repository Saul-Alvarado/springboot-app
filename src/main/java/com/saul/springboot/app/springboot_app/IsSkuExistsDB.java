package com.saul.springboot.app.springboot_app;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = IsSkuExistsDbValidatior.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsSkuExistsDB {

    String message() default "el campo sku ya existe";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
