//package com.zynn.common.core.utils;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.TreeSet;
//
///**
// * 功能描述: redis存储和获取List集合
// *
// * @author: 刘猛
// * @date: 2018/9/19 10:57
// **/
//@Component
//public class JedisClientSingle {
//    @Autowired
//    JedisCluster jedisCluster;
//
//    /**
//     * 设置List集合
//     *
//     * @params key
//     * @params list
//     */
//    public void setList(String key, List<?> list, int validTime) {
//        try {
//            if (!CollectionUtils.isEmpty(list)) {
//                jedisCluster.setex(key.getBytes(), validTime, SerializeUtil.serializeList(list));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取List集合
//     *
//     * @params key
//     * @return
//     */
//    public List<?> getList(String key) {
//        if (jedisCluster == null || !jedisCluster.exists(key)) {
//            return null;
//        }
//        byte[] data = jedisCluster.get(key.getBytes());
//        return SerializeUtil.unserializeList(data);
//    }
//
//    /**
//     * 查询key
//     *
//     * @params pattern
//     * @return
//     */
//    public TreeSet<String> keys(String pattern) {
//        TreeSet<String> keys = new TreeSet<>();
//        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
//        for (String k : clusterNodes.keySet()) {
//            JedisPool jp = clusterNodes.get(k);
//            Jedis connection = jp.getResource();
//            keys.addAll(connection.keys(pattern));
//        }
//        return keys;
//    }
//}
