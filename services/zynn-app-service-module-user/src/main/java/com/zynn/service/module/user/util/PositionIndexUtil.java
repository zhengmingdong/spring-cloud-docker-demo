package com.zynn.service.module.user.util;

import com.zynn.common.core.constant.EsIndexConstant;
import com.zynn.common.core.utils.ElasticSearchUtil;
import com.zynn.common.core.utils.ObjectUtil;
import com.zynn.common.pojo.dto.event.EventPositionDTO;
import org.elasticsearch.common.geo.GeoPoint;

import java.util.Map;
import java.util.Objects;

/**
 * 索引工具
 * @author wangyulin
 * @date 2019年3月14日14:58:50
 */
public class PositionIndexUtil {

    /**
     * 转换数据-将Map对象转换成EventDTO
     *
     * @param source
     * @return
     */
    public static EventPositionDTO convertToPositionDTO(Map<String, Object> source) {
        String latitudeLongitude = ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.LATITUDE_LONGITUDE));
        GeoPoint geoPoint = ElasticSearchUtil.latLonConvert(latitudeLongitude);
        return new EventPositionDTO()
                .setCountry(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.COUNTRY)))
                .setProvince(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.PROVINCE)))
                .setCity(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.CITY)))
                .setWantCount(ObjectUtil.objectToInteger(source.get(EsIndexConstant.PositionIndex.Properties.WANT_COUNT)))
                .setComeCount(ObjectUtil.objectToInteger(source.get(EsIndexConstant.PositionIndex.Properties.COME_COUNT)))
                .setLocation(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.LOCATION)))
                .setPosition(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.POSITION)))
                .setLongitude(Objects.nonNull(geoPoint) ? geoPoint.getLon() : null)
                .setLatitude(Objects.nonNull(geoPoint) ? geoPoint.getLat() : null)
                .setLatitudeLongitude(latitudeLongitude)
                .setPositionId(ObjectUtil.objectToLong(source.get(EsIndexConstant.PositionIndex.Properties.POSITION_ID)));
    }
}
