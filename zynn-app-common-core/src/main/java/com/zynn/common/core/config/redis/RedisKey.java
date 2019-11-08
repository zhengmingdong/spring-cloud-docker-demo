package com.zynn.common.core.config.redis;

import lombok.Data;

/**
 * @author 刘天元
 * @description
 * @create 2018-06-22 16:13
 **/
@Data
public class RedisKey {

    /**
     * key的前缀
     */
    private String prefix;

    /**
     * prefix+key组成最终的键值
     */
    private String key;

    public RedisKey(String prefix) {
        this.prefix = prefix;
        this.key = "";
    }

    public RedisKey(String prefix, String key) {
        this.prefix = prefix;
        this.key = key;
    }

    public RedisKey(String prefix, Long key) {
        this.prefix = prefix;
        this.key = key.toString();
    }

    public static RedisKey of(String prefix,String key){
        return new RedisKey(prefix,key);
    }

    public static RedisKey of(String prefix,Long key){
        return new RedisKey(prefix,key);
    }


    public String getRealKey() {
        return prefix + "_" + key;
    }



}
