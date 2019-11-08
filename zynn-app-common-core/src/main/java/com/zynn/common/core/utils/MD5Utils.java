package com.zynn.common.core.utils;

import java.security.MessageDigest;

/**
 *  md5 工具类
 *  @author 杨岳
 *  @date 2018-07-9 19:09
 */
public class MD5Utils {

    /**
     *  Md5 加密
     * @param data
     * @return
     */
    public static String md5Encrypt(String data) {
        String resultString = null;
        try {
            resultString = new String(data);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    public static void main(String[] args) {
//        Double longitude = 106.5179205098;
//        Double latitude = 29.50780611;
//        MapLocationDetailBO locationInfo = MapPositionInfoUtil.getLocationInfo(longitude + "," + latitude);
//        LocationReturnBO addressComponent = (Objects.nonNull(locationInfo) && Objects.nonNull(locationInfo.getAddressComponent())) ? locationInfo.getAddressComponent() : null;
//        String cityName = Objects.equals(addressComponent.getCity(),"[]") ? addressComponent.getProvince() : addressComponent.getCity();
//        System.out.println(cityName);
        System.out.println(md5Encrypt("1264984690273079296"));
    }

    /**
     *  md5 简单加密算法  超简版本
      * @param bytes   待加密的byte数组
     * @return
     */
    private static String byte2hexString(byte[] bytes) {
        StringBuffer bf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                bf.append("0");
            }
            bf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return bf.toString();
    }


}
