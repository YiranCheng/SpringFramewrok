package com.gupao.edu.vip.study.framework.annotation;

import java.lang.annotation.*;

/**
 * @author yiran
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyService {
    String value() default "";
}
