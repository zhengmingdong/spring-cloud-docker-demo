package com.zynn.common.core.utils;

import com.zynn.common.core.bo.MapLocationDetailBO;
import com.zynn.common.core.config.push.PushObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


/**
 * @author 杨岳
 * @date 2018/7/30 17:29
 */

public class RandomStringUtilsTest {

    @Test
    public void test28(){

//        List<String> data = Arrays.asList(new String[]{"113.941064,22.52433", "114.279474,29.877509"});
//
//        List<MapLocationDetailBO> locationReturnBO = MapPositionInfoUtil.getLocationInfoList(data);

        MapLocationDetailBO locationInfo = MapPositionInfoUtil.getLocationInfo("103.8278660000" + "," + "30.6740820000");

        System.out.println(1111111);
    }

    @Test
    public void aaa(){
//        System.out.println(changePriviceToCity("杭州"));
    }

//    private String changePriviceToCity(String cityName) {
//        switch (cityName) {
//            case "上海市":cityName="上海城区";
//                break;
//            case "北京市":cityName="北京城区";
//                break;
//            default:
//                break;
//        }
//        return cityName;
//    }
}