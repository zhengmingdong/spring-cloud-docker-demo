package com.zynn.common.core.utils;


import com.alibaba.fastjson.JSONObject;
import com.zynn.common.core.bo.MapLocationDetailBO;
import com.zynn.common.core.bo.MapResultBO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 逆编码经纬度至地理信息
 */
@Data
@Slf4j
public class MapPositionInfoUtil {

    /**
     * 主地址
     */
    private final static String URL = "https://restapi.amap.com/v3/geocode/regeo";

    /**
     * 行政区域查询url
     */
    private final static String URL1 = "https://restapi.amap.com/v3/config/district";

    /**
     * 返回格式
     */
    private static final String OUTPUT = "JSON";

    /**
     * 查询的范围
     */
    private static final String RADIUS = "1000";

    /**
     * 查询基础数据,可选择为all
     */
    private static final String EXTENSIONS  = "base";

    /**
     * 高德地图密码Key
     */
    private static final String KEY="d22f05cf7a85fc6825f596fb25c4cda9";

    /**
     * 可批量查询
     */
    private static final String BATCH = "true";

    /**
     * @param position "经度,纬度"
     * @return
     */
    public static MapLocationDetailBO getLocationInfo(String position){

        String url = URL+"?"+"output="+OUTPUT+"&location="+position+"&key="+KEY+
                "&radius="+RADIUS+"&extensions="+EXTENSIONS+"&batch="+ BATCH;

        String result = QiniuOperateUtil.sentHttpRequestToGetImageInfo(url);
        log.info("高德地图返回的经纬度位置信息:"+result);
        MapResultBO mapResultBO = JSONObject.parseObject(result,MapResultBO.class);

        if(Objects.nonNull(mapResultBO)){
            List<MapLocationDetailBO> mapLocationDetailBOList= mapResultBO.getRegeocodes();

            return mapLocationDetailBOList!=null?mapLocationDetailBOList.stream().findFirst().orElse(null):null;
        }

        return null;
    }

    /**
     * @param province
     * @return
     */
    public static String districtEnquiry(String province){
        String url1 = URL1+"?"+"output="+OUTPUT+"&keywords="+province+"&key="+KEY+"&extensions="+EXTENSIONS+"&subdistrict"+0;
        String result = QiniuOperateUtil.sentHttpRequestToGetImageInfo(url1);
        return result;
    }

    /**
     * @param positions List<"经度,纬度">
     * @return
     */
    public static  List<MapLocationDetailBO> getLocationInfoList(List<String> positions){

        String position = StringUtils.join(positions, "|");

        String url = URL+"?"+"output="+OUTPUT+"&location="+position+"&key="+KEY+
                "&radius="+RADIUS+"&extensions="+EXTENSIONS+"&batch="+ BATCH;

        String result = QiniuOperateUtil.sentHttpRequestToGetImageInfo(url);
        log.info("高德地图返回的经纬度位置信息:"+result);
        MapResultBO mapResultBO = JSONObject.parseObject(result,MapResultBO.class);

        if(Objects.nonNull(mapResultBO)){
            List<MapLocationDetailBO> mapLocationDetailBOList= mapResultBO.getRegeocodes();

            return mapLocationDetailBOList;
        }

        return null;
    }

}
