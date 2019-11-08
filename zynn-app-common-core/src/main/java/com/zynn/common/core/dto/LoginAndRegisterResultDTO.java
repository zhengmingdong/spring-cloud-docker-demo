package com.zynn.common.core.dto;

import lombok.Data;


/**
 * 注册或登录返回数据
 * @author 刘天元
 * @since 2018-5-31 14:05:18
 */
@Data
public class  LoginAndRegisterResultDTO {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户手机号
     */
    private String cellphone;

    /**
     * 用户头像
     */
    private String userPic;

    /**
     * 用户性别:男-1;女-2
     */
    private Integer userSex;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 用户学校id
     */
    private String schoolId;

    /**
     * 是否绑定了手机号
     */
    private Integer isSkipCellphone;
}
