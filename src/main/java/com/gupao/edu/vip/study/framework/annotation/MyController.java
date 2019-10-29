package com.gupao.edu.vip.study.framework.annotation;

import java.lang.annotation.*;

/**
 * @author yiran
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {
    String value() default "";
}
