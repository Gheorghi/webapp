package com.endava.webapp.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniquePhoneValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPhoneUniqueness {
    String message() default "Phone number is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
