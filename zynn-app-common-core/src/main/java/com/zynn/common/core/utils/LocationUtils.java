package com.zynn.common.core.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 计算经纬度的距离工具类
 * @author zhanghao
 * @date 2019/4/30 18:00
 **/
@Slf4j
public class LocationUtils {


    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }



    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        log.debug("lat1 = {} , lng1 = {} , lat2 = {} , lng2 = {} , distance = {} ",lat1,lng1,lat2,lng2,s);
        return s;
    }
}
