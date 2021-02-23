package io.zsy.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebLogAnnotation {

    /**
     * 日志描述信息
     */
    String description() default "";
}
