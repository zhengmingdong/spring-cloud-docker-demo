package com.zynn.common.core.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 袁毅雄
 * @description
 * @date 2019/7/8
 */
@Data
public class UserRecallDTO {
    /**
     * 被召回的用户id
     */
    private Long beRecallUserId;

    /**
     * 被召回的用户好友数
     */
    private Integer friendNumber;

    /**
     * 被召回的用户好友id
     */
    private List<Long> friendUserIds;
}
