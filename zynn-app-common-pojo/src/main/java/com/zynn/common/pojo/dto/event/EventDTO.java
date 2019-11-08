package com.zynn.common.pojo.dto.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 袁毅雄
 * @description
 * @date 2019/7/3
 */
@Data
@Accessors(chain = true)
public class EventDTO {

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
     * 动态资源类型
     */
    private Integer sourceType;

    /**
     * 动态展示类型
     */
    private Integer showType;

    /**
     * 发布的详细位置
     */
    private String location;

    /**
     * 圈子id
     */
    private Long clusterId;
}
