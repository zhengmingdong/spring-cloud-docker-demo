package com.zynn.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author 刘天元
 */
public class JsonUtil {
    private static final SerializeConfig CONFIG;

    static {
        CONFIG = new SerializeConfig();
        // compatible with the java.util.Date and the java.sql.Date
        CONFIG.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        CONFIG.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    /**
     * set the default value of the object which the value is null
     */
    private static final SerializerFeature[] FEATURES = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteNullStringAsEmpty,
    };

    /**
     * list to json
     *
     * @param list the list that will transform to json string
     * @return the json string of list transform
     */
    public static String list2json(List list) {
        return JSON.toJSONString(list);
    }

    /**
     * map to json
     *
     * @param map the map that will transform to json string
     * @return the json string of map transform
     */
    public static String map2json(Map map) {
        return JSONObject.toJSONString(map);
    }

    /**
     * object array to json
     *
     * @param objects the object array that will transform to json string
     * @return the json string of array transform
     */
    public static String array2json(Object[] objects) {
        return JSON.toJSONString(objects);
    }

    /**
     * object to json
     *
     * @param object the object that will transform to json string
     * @return the json string of object
     */
    public static String object2json(Object object) {
        return JSON.toJSONString(object, CONFIG, FEATURES);
    }


    /**
     * json to list
     *
     * @param json  the json string that will transform to list
     * @param clazz the class of the list's element
     * @param <T>   the generic of the class
     * @return the list that json string transform
     */
    public static <T> List<T> json2list(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * json to map
     *
     * @param json json string that will transform to map
     * @return the map fo json string
     */
    public static Map json2map(String json) {
        return JSONObject.parseObject(json);
    }


    /**
     * json string to object array
     *
     * @param json  the json string will transform to object array
     * @param clazz the class of the json will transform
     * @param ts    the real object array
     * @param <T>   the real object
     * @param json
     * @param clazz
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> T[] json2array(String json, Class<T> clazz, T[] ts) {
        return JSON.parseArray(json, clazz).toArray(ts);
    }

    /**
     * json string to object
     *
     * @param json  the json string that will transform to object
     * @param clazz the class that json will transform
     * @param <T>   the object class
     * @return the object of json string
     */
    public static <T> Object json2object(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    /**
     * object 转换成 json 剔除不需要的字段
     *
     * @param object
     * @param attributes 需要移除的字段
     * @return
     */
    public static String objectToJson(Object object, String... attributes) {
        String s1 = JSONObject.toJSONString(object);
        JSONObject jsonObject = JSONObject.parseObject(s1);
        for (String str : attributes) {
            jsonObject.remove(str);
        }
        return jsonObject.toJSONString();
    }

    /**
     * object 转换成 json 剔除不需要的字段
     *
     * @param list
     * @param attributes 需要移除的字段
     * @return
     */
    public static <T> List<String> listToJson(List<T> list,String... attributes) {
        List<String> results=new ArrayList<>();
        for (Object obj : list) {
            String s1 = JSONObject.toJSONString(obj);
            JSONObject jsonObject = JSONObject.parseObject(s1);
            for (String str : attributes) {
                jsonObject.remove(str);
            }
            results.add(jsonObject.toJSONString());
        }
        return results;
    }

    public static String object2jsonWithDate(Object object) {
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss.SSS");
    }
}
