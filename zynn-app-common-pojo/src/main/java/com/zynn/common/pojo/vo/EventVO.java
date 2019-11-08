package com.zynn.common.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhengmingdong
 * @date 2019-03-22 15:53
 */
@Data
@Accessors(chain = true)
public class EventVO {

    /**
     * 动态id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long eventId;

    /**
     * 动态资源
     */
    private String url;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 动态类型
     */
    private Integer sourceType;

    /**
     * 动态类型
     */
    private Integer showType;

    /**
     * 发布的详细位置
     */
    private String location;

    /**
     * 资源信息
     */
    private List<BsEventResourceVO> bsEventResourceVOList;

}
