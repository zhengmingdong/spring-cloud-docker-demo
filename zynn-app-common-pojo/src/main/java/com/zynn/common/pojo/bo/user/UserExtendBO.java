package com.zynn.common.pojo.bo.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author zhengmingdong
 * @date 2019-03-30 17:43
 */
@Data
@Accessors(chain = true)
public class UserExtendBO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 情感状态，0单身，1密码，2恋爱
     */
    private Integer affectiveState;

    /**
     * 生日
     */
    private Date birthday;

}
