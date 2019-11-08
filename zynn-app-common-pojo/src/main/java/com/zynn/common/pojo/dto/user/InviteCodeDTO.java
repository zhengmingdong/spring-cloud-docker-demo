package com.zynn.common.pojo.dto.user;

import lombok.Data;

/**
 * @author wangyulin
 * @date 2019年6月6日11:04:51
 * 邀请码活动DTO
 */
@Data
public class InviteCodeDTO {

    private String inviteCode;

    private Long schoolId;

    private Long userId;

}
