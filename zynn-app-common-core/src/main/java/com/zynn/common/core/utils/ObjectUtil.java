package com.zynn.common.core.utils;

import java.util.Objects;

/**
 * @ClassName NumberUtil
 * @Description TODO
 * @Author 刘猛
 * @Date 2018/10/18 10:04
 **/
public class ObjectUtil {

    /**
     * 转换数据-将对象转换成String类型
     *
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        String str = Objects.toString(object, "");
        String nullStr = "null";
        if (nullStr.equals(str)) {
            str = "";
        }
        return str;
    }

    /**
     * 转换数据-将对象转换成Long类型
     *
     * @param object
     * @return
     */
    public static Long objectToLong(Object object) {
        return Long.parseLong(Objects.toString(object, "0"));
    }

    /**
     * 转换数据-将对象转换成Integer类型
     *
     * @param object
     * @return
     */
    public static Integer objectToInteger(Object object) {
        return Integer.parseInt(Objects.toString(object, "0"));
    }
}

