package com.zynn.common.core.annotation;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务分布式锁
 * @Author zhanghao
 * @Date  2019/3/25
 **/
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaskLock {

    /**
     * 同时并发数量
     * @return
     */
    int concurrentNumber() default 1;


    /**
     * 持有锁时间
     * @return
     */
    long leaseTime() ;


    /**
     * 等待时间，默认为 不等待
     * @return
     */
    long waitTime() default 0;


    /**
     * 时间单位，默认为秒
     */
    TimeUnit unit() default TimeUnit.SECONDS;


}
