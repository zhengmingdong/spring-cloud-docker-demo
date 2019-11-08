//package com.zynn.common.core.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @author 刘凯
// * @description
// * @date 2018/8/13 下午2:27
// */
//@Component
//public class SendRedisUtil {
//    @Autowired
//    private JedisCluster jedisCluster;
//
//    public void sendToRedis(String key,String value,int time){
//        ThreadPoolManager.getInstance().execute(
//                () -> {jedisCluster.setex(key,time,value);}
//        );
//    }
//}
