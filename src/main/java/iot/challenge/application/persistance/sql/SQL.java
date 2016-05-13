package iot.challenge.application.persistance.sql;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.PARAMETER;


@BindingAnnotation
@Target({ PARAMETER }) @Retention(RUNTIME)
public @interface SQL {
}
