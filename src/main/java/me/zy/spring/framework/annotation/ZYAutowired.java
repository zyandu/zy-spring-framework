package me.zy.spring.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZYAutowired {
    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    //boolean required() default true;

    String value() default "";
}
