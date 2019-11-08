package com.zynn.common.pojo.dto.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author zhengmigndong
 * @date 2019年3月8日10:54:44
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventPositionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 位置id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long positionId;

     /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 想去的数量
     */
    private Integer wantCount;

    /**
     * 已去数量
     */
    private Integer comeCount;

    /**
     * 地理位置详细信息
     */
    private String location;

    /**
     * 地理位置
     */
    private String position;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经纬度
     */
    @JsonIgnore
    private String latitudeLongitude;

    /**
     * 地理位置_查询
     */
    private String positionQuery;

    /**
     * 与我当前位置的距离
     */
    private Double distance;


    /**
     * 地点图片
     */
    private String positionUrl;

}
