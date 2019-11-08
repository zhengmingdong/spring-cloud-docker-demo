package com.zynn.common.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 星座
 */
public class ConstellationUtil {

    /**
     * 获取星座名称
     * @param month 月
     * @param day 日
     * @return
     */
    public static Map<String,Object> constellation(int month, int day) {
        String constellation = "";
        Integer constellationId = null;
        Map<String,Object> map = new HashMap<String,Object>();
        if (month == 1 && day >= 20 || month == 2 && day <= 18) {
            constellation = "水瓶座";
            constellationId = 11;
        }
        if (month == 2 && day >= 19 || month == 3 && day <= 20) {
            constellation = "双鱼座";
            constellationId = 12;
        }
        if (month == 3 && day >= 21 || month == 4 && day <= 19) {
            constellation = "白羊座";
            constellationId = 1;
        }
        if (month == 4 && day >= 20 || month == 5 && day <= 20) {
            constellation = "金牛座";
            constellationId = 2;
        }
        if (month == 5 && day >= 21 || month == 6 && day <= 21) {
            constellation = "双子座";
            constellationId = 3;
        }
        if (month == 6 && day >= 22 || month == 7 && day <= 22) {
            constellation = "巨蟹座";
            constellationId = 4;
        }
        if (month == 7 && day >= 23 || month == 8 && day <= 22) {
            constellation = "狮子座";
            constellationId = 5;
        }
        if (month == 8 && day >= 23 || month == 9 && day <= 22) {
            constellation = "处女座";
            constellationId = 6;
        }
        if (month == 9 && day >= 23 || month == 10 && day <= 23) {
            constellation = "天秤座";
            constellationId = 7;
        }
        if (month == 10 && day >= 24 || month == 11 && day <= 22) {
            constellation = "天蝎座";
            constellationId = 8;
        }
        if (month == 11 && day >= 23 || month == 12 && day <= 21) {
            constellation = "射手座";
            constellationId = 9;
        }
        if (month == 12 && day >= 22 || month == 1 && day <= 19) {
            constellation = "摩羯座";
            constellationId = 10;
        }
        map.put("constellationName",constellation);
        map.put("constellationId",constellationId);
        return map;
    }
}
