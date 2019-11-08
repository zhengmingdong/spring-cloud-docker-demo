package com.zynn.common.pojo.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangyulin
 * @description 用户通讯录DTO
 * @create 2018-07-29 10:20
 **/
@Data
@Accessors(chain = true)
public class UserAddressListDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户手机号
     */
    private String userCellphone;

    /**
     * 用户通讯录手机号
     */
    private Long[] cellphoneList;
}
