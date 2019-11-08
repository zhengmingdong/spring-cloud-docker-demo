package com.zynn.common.core.aspect;

/**
 * @author zhanghao
 * @date 2019/5/21 18:16
 **/

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zynn.common.core.annotation.OperationSub;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

/**
 * 操作切割切片
 *
 * @Author zhanghao
 * @date 2019/3/26 11:25
 **/
@Aspect
@Component
@Slf4j
@Order(Integer.MIN_VALUE)
public class OperationSubAspect {

    /**
     * 当方法上同时标注 OperationSub 注解 , 并且入参为List时拦截
     *
     * @return void
     * @Author zhanghao
     * @Date 2019/3/26
     * @Param []
     **/
    @Pointcut("@annotation(com.zynn.common.core.annotation.OperationSub) && args(java.util.List)")
    public void operationSub() {
    }


    @Around("operationSub()")
    public Object operationSub(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("QuerySubAspect start ...");
        List paramLsit = null;
        if (joinPoint.getArgs().length == 1) {
            paramLsit = (List) joinPoint.getArgs()[0];
        } else {
            return joinPoint.proceed();
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        final Class<?> returnType = method.getReturnType();

        final OperationSub operationSub = method.getAnnotation(OperationSub.class);
        if (returnType.isAssignableFrom(Map.class)) {
            final Map resultMap = Maps.newHashMap();
            subList(paramLsit, operationSub.operationMaxNum(), subList -> {
                final Map result;
                try {
                    result = (Map) joinPoint.proceed(new Object[]{subList});
                    resultMap.putAll(result);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            });
            return resultMap;
        }else if (returnType.equals(List.class)){
            final List resultList = Lists.newArrayList();
            subList(paramLsit, operationSub.operationMaxNum(), subList -> {
                try {
                    List result = (List) joinPoint.proceed(new Object[]{subList});
                    resultList.addAll(result);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            });
            return resultList;
        }else if (returnType.equals(Set.class)){
            final Set resultList = Sets.newHashSet();
            subList(paramLsit, operationSub.operationMaxNum(), subList -> {
                try {
                    Set result = (Set) joinPoint.proceed(new Object[]{subList});
                    resultList.addAll(result);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            });
            return resultList;
        }else if (returnType.equals(Void.TYPE)){
            subList(paramLsit, operationSub.operationMaxNum(), subList -> {
                try {
                    joinPoint.proceed(new Object[]{subList});
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            });
            return  new Object();
        }
        return joinPoint.proceed();
    }


    private void subList(List paramList, int maxNum , Consumer<List> consumer) {
        if (CollectionUtils.isEmpty(paramList)){
            return;
        }
        final List<List> partitionList = Lists.partition(paramList, maxNum);

        partitionList.forEach(l -> {
            consumer.accept(l);
        });


//        final int size = paramList.size();
//        final int num = size / maxNum;
//        final int remainder = size % maxNum;
//        if (num == 0) {
//            consumer.accept(paramList);
//        } else {
//            for (int i = 0; i <= num; i++) {
//                int start = i * maxNum;
//                int end = 0;
//                if (i == num) {
//                    end = start + remainder;
//                } else {
//                    end = (i + 1) * maxNum;
//                }
//                consumer.accept(paramList.subList(start,end));
//            }
//        }
    }
}
