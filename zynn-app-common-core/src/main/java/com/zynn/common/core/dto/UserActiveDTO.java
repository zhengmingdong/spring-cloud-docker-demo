package com.zynn.common.core.dto;

import lombok.Data;

/**
 * @author 袁毅雄
 * @description 活跃用户
 * @date 2019/7/7
 */
@Data
public class UserActiveDTO {
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 最后的活跃时间 yyyy-MM-dd
     */
    private String latestTime;

    /**
     * 日期差异
     */
    private Integer dateDiff;
}
