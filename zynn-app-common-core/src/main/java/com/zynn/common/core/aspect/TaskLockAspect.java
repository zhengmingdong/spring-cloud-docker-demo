package com.zynn.common.core.aspect;

import com.google.common.collect.Maps;
import com.zynn.common.core.annotation.TaskLock;
import com.zynn.common.core.config.redis.RedisKey;
import com.zynn.common.core.constant.RedisLockConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 定时器分布式锁切片
 * @Author zhanghao
 * @date 2019/3/26 11:25
 **/
@Aspect
@Component
@Slf4j
public class TaskLockAspect {



    /**
     * 当方法上同时标注着两个注解时被拦截
     * @Author zhanghao
     * @Date  2019/3/26
     * @Param []
     * @return void
     **/
    @Pointcut("@annotation(com.zynn.common.core.annotation.TaskLock) && @annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void taskLockPointut(){}


    @Resource
    private RedissonClient redissonClient;



    @Around("taskLockPointut()")
    public Object taskLock(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("task lock aspect start ...");

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        final Method method = signature.getMethod();
        final TaskLock taskLock = method.getAnnotation(TaskLock.class);

        if (taskLock == null) {
            return joinPoint.proceed();
        }

        final String redisKey = new RedisKey(RedisLockConstant.TASK_LOCK_PREFIX,method.getDeclaringClass().getSimpleName().concat("_").concat( method.getName())).getRealKey();

        final RPermitExpirableSemaphore semaphore = getSemaphore(redisKey, taskLock);

        log.info("redis key  = {} , availablePermits = {} ",redisKey,semaphore.availablePermits());

        //尝试获取锁
        final String acquire = semaphore.tryAcquire(taskLock.waitTime(), taskLock.leaseTime(), taskLock.unit());

        //如果为空，则为获取锁失败
        if (!StringUtils.isEmpty(acquire)){
            final Object result = joinPoint.proceed();
            Mono.delay(Duration.ofMillis(taskLock.unit().toMillis(taskLock.leaseTime())))
                    .subscribe(l -> {
                        log.info("release lock ,redis key  = {} ",redisKey);
                       semaphore.release(acquire);
                    });
            return result;
        }
        return new Object();
    }

    Map<String,RPermitExpirableSemaphore> semaphoreMap = Maps.newHashMap();

    public RPermitExpirableSemaphore getSemaphore(String redisKey,TaskLock taskLock){
        final RPermitExpirableSemaphore permitExpirableSemaphore = redissonClient.getPermitExpirableSemaphore(redisKey);
        permitExpirableSemaphore.trySetPermits(taskLock.concurrentNumber());
        semaphoreMap.put(redisKey,permitExpirableSemaphore);
        return  permitExpirableSemaphore;
    }


}
