package com.zynn.common.pojo.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李琦
 *
 * 推荐动态实体
 */
@Data
public class RcmdEventDTO implements Serializable {

    /** 用户ID */
    private Long userId;

    /** 动态ID */
    private Long eventId;

    /** 动态文字内容 */
    private String content;

    /** 更新时间 */
    private String updateTime;
}
