package com.zynn.common.core.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.UncaughtExceptionHandlers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.*;

/**
 * @author 刘天元
 * @description 创建线程池
 * @create 2018-07-19 17:24
 **/
public class ThreadPoolFactory {
    /**
     * 根据cpu的数量动态的配置核心线程数和最大线程数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程数 = CPU核心数 + 1
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    /**
     * 线程池最大线程数 = CPU核心数 * 2 + 1
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 非核心线程闲置时超时1s
     */
    private static final int KEEP_ALIVE = 1;

    /**
     * 队列大小
     */
    private static final int BLOCKING_QUEUE_CAPACITY = 50;

    /**
     * 创建线程池
     * @param nameFormat 自定义线程命名前缀
     * @return
     */
    public static ThreadPoolExecutor createThreadPoolExecutor(String nameFormat) {
        return createThreadPoolExecutor(nameFormat, null);
    }

    /**
     * 创建线程池
     * @param nameFormat 自定义线程命名前缀
     * @param blockingQueueCapacity 线程池阻塞队列大小
     * @return
     */
    public static ThreadPoolExecutor createThreadPoolExecutor(String nameFormat, Integer blockingQueueCapacity) {
        if(StringUtils.isBlank(nameFormat)) {
            nameFormat = "通用线程池-%d";
        }
        if(blockingQueueCapacity == null || blockingQueueCapacity == 0) {
            blockingQueueCapacity = BLOCKING_QUEUE_CAPACITY;
        }
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(nameFormat)
                .build();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(blockingQueueCapacity), namedThreadFactory, new CustomizedAbortPolicy());

        return threadPoolExecutor;
    }

    /**
     * 线程池拒绝策略:阻塞式地将任务添加至工作队列中
     */
    private static class CustomizedAbortPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.out.println(Thread.currentThread().getName()+"任务被拒绝");
            if (!e.isShutdown()) {
                try {
                    e.getQueue().put(r);
                } catch (InterruptedException exception) {
                    e.execute(r);
                }
            }
        }
    }

}
