package com.gupao.edu.vip.study.framework.annotation;

import java.lang.annotation.*;

/**
 * @author yiran
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {
    String value() default "";
}
