package com.zynn.common.core.utils;

import com.zynn.common.core.exception.TryGetLockFailExcetion;
import com.zynn.common.core.function.Operation;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @Author zhanghao
 * @Description  Redis分布式锁
 * @Date  2019/4/16
 **/

@Service
public class RedisDistributeLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributeLock.class);


    private static RedissonClient redissonClient;

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        RedisDistributeLock.redissonClient = redissonClient;
    }


    /**
     * 尝试获取锁，最多等待 waitTime 时间
     * 注意：该方法获取的锁不会自动解锁，必须要调用 @see unLock 方法进行解锁
     * @param key
     * @param waitTime
     * @param timeUnit
     * @return
     */
    public static boolean tryLock(String key,Long waitTime,TimeUnit timeUnit){
        final RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(waitTime,timeUnit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Author zhanghao
     * @Description 解锁Key
     * @Date  2019/4/17
     * @Param [key]
     * @return void
     **/
    public static void unLock(String key){
        final RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }


    /**
     * @Author zhanghao
     * @Description 尝试进行加锁，最多等待 waitTime 时间，持有锁到 leaseTime 后自动解锁
     * @Date  2019/4/16
     * @Param [key, waitTime, leaseTime, timeUnit] rediskey , 等待时间 ，锁持有时间，时间单位
     * @return boolean
     **/
    public static boolean tryLock(String key,Long waitTime,Long leaseTime,TimeUnit timeUnit) throws InterruptedException {
        final RLock lock = redissonClient.getLock(key);
        return  lock.tryLock(waitTime, leaseTime, timeUnit);
    }


    /**
     * @Author zhanghao
     * @Description 尝试进行加锁，最多等待 waitTime 时间，执行operation操作，然后进行解锁
     * @Date  2019/4/16
     * @Param [key, waitTime, timeUnit, operation] rediskey , 等待时间 ，时间单位，进行的操作
     * @return void
     **/
    public static void tryLock(String key, Long waitTime, TimeUnit timeUnit, Operation operation) throws TryGetLockFailExcetion {
        final RLock lock = redissonClient.getLock(key);
        final boolean result;
        try {
            result = lock.tryLock(waitTime, timeUnit);
            if (result){
                try {
                    operation.operation();
                }finally {
                    lock.unlock();
                }
            }else{
                throw new TryGetLockFailExcetion();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Author zhanghao
     * @Description 尝试进行加锁，最多等待 waitTime 时间，执行sussessOperation操作，然后进行解锁,如果获取锁失败，则执行 failOperation 操作
     * @Date  2019/4/17
     * @Param [key, waitTime, timeUnit, sussessOperation, failOperation] rediskey , 等待时间 ，时间单位，成功操作，失败操作
     * @return void
     **/
    public static void tryLock(String key, Long waitTime, TimeUnit timeUnit, Operation sussessOperation,Operation failOperation)  {
        final RLock lock = redissonClient.getLock(key);
        final boolean result;
        try {
            result = lock.tryLock(waitTime, timeUnit);
            if (result){
                try {
                    sussessOperation.operation();
                }finally {
                    lock.unlock();
                }
            }else{
                failOperation.operation();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author zhanghao
     * @Description 尝试进行加锁，最多等待 waitTime 时间，执行supplier操作，然后进行解锁
     * @Date  2019/4/16
     * @Param [key, waitTime, timeUnit, operation] rediskey , 等待时间 ，时间单位，要进行的操作
     * @return R
     **/
    public static <R> R tryLock(String key, Long waitTime, TimeUnit timeUnit, Supplier<R> supplier) throws  TryGetLockFailExcetion {
        final RLock lock = redissonClient.getLock(key);
        try {
            final boolean result = lock.tryLock(waitTime, timeUnit);
            if (result){
                try {
                    return  supplier.get();
                }finally {
                    lock.unlock();
                }
            }else{
                throw new TryGetLockFailExcetion();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Author zhanghao
     * @Description 尝试进行加锁，最多等待 waitTime 时间，执行sussessSupplier操作，然后进行解锁，获取锁失败则执行 failSupplier 操作
     * @Date  2019/4/17
     * @Param [key, waitTime, timeUnit, sussessSupplier, failSupplier]  rediskey , 等待时间 ，时间单位，成功操作，失败操作
     * @return R
     **/
    public static <R> R tryLock(String key, Long waitTime, TimeUnit timeUnit, Supplier<R> sussessSupplier,Supplier<R> failSupplier)  {
        final RLock lock = redissonClient.getLock(key);
        try {
            final boolean result = lock.tryLock(waitTime, timeUnit);
            if (result){
                try {
                    return  sussessSupplier.get();
                }finally {
                    lock.unlock();
                }
            }else{
                return  failSupplier.get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
