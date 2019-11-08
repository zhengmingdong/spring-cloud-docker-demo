package com.zynn.common.core.utils;

import java.util.concurrent.ExecutorService;


/**
 * @author 刘天元
 * 线程池管理类
 */
public class ThreadPoolManager {

    /**
     * 要确保该类只有一个实例对象，避免产生过多对象消费资源，所以采用单例模式
     */
    private ThreadPoolManager() {
    }

    private static ThreadPoolManager sInstance;

    public synchronized static ThreadPoolManager getInstance() {
        if (sInstance == null) {
            sInstance = new ThreadPoolManager();
        }
        return sInstance;
    }

    /**
     * 线程池的对象
     */
    private ExecutorService executor;


    /**
     * 使用线程池，线程池中线程的创建完全是由线程池自己来维护的，我们不需要创建任何的线程
     * 我们所需要做的事情就是往这个池子里面丢一个又一个的任务
     * @param r
     */
    public void execute(Runnable r) {
        if (executor == null) {
            executor = ThreadPoolFactory.createThreadPoolExecutor("通用线程池-%d");
        }
        // 把一个任务丢到了线程池中
        executor.execute(r);
    }

}