package com.zynn.common.core.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *@ClassName FutureQueryConstUtil
 *@Description TODO
 *@Author 刘猛
 *@Date 2018/12/6 11:28
 **/
public class FutureQueryConstUtil {
    /**
     * 功能描述: 多个线程中阻塞合并list集合
     * @param: [listFuture, result]
     * @return: void
     * @author: 刘猛
     * @date: 2018/12/11 11:08
     **/
    public static <T> void  futureGetListResult(List<Future<List<T>>> listFuture, List<T> result){
        listFuture.stream().forEach(future ->{
            try {
                result.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 功能描述: 多线程中阻塞合并map集合
     * @param: [listFuture, result]
     * @return: void
     * @author: 刘猛
     * @date: 2018/12/11 11:08
     **/
    public static <KEY,VALUE> void  futureGetMapResult(List<Future<Map<KEY,VALUE>>> listFuture, Map<KEY,VALUE> result){
    listFuture.stream().forEach(future ->{
        try {
            result.putAll(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    });
}
}
