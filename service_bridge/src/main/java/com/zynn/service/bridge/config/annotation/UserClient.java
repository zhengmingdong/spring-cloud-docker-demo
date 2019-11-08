package com.zynn.service.bridge.config.annotation;

import com.zynn.service.bridge.config.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 用户服务连接注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FeignClient(ServiceNameConstant.USER_SERVICE)
public @interface UserClient {


    @AliasFor(annotation = FeignClient.class,attribute = "fallbackFactory")
    Class<?> fallbackFactory() default void.class;

    @AliasFor(annotation = FeignClient.class,attribute = "path")
    String value() default "" ;

    @AliasFor(annotation = FeignClient.class,attribute = "primary")
    boolean primary() default true;

}
