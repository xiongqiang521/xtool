package com.xq.xtool.http.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpApiPath {
    @AliasFor("path")
    String value() default "";

    @AliasFor("value")
    String path() default "";

    RequestMethod method() default RequestMethod.GET;

}
