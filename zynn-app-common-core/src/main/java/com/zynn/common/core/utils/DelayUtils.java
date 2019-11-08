package com.zynn.common.core.utils;

import com.zynn.common.core.function.Operation;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

/**
 * 延迟工具类
 * @Author zhanghao
 * @date 2019/3/26 17:59
 **/
public class DelayUtils {

    /**
     * 延迟时间后执行
     * @Author zhanghao
     * @Date  2019/3/26
     * @Param [supplier, time, unit] 需要执行的方法, 延迟数值，时间单位
     * @return void
     **/
    public static void delay(Integer amount, ChronoUnit unit, Operation operation ){
        Mono.delay(Duration.of(amount,unit))
                .subscribe(l -> {
                    operation.operation();
                });
    }

}
