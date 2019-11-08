package com.zynn.common.pojo.vo;

import lombok.Data;

/**
 * @author zhengmingdong
 * @date 2019-03-26 13:30
 */
@Data
public class UserEsVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String headPic;
}
