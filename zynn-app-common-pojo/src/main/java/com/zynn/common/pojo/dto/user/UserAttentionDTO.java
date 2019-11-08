package com.zynn.common.pojo.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 袁毅雄
 * @description 用户关系
 * @date 2019/3/22
 */
@Data
@EqualsAndHashCode
public class UserAttentionDTO extends UserDTO {

    /**
     * 关系:
     * 0-[a、b毫无关系]
     * 1-[a用户单向关注b用户]
     * 2-[b用户单向关注a用户]
     * 3-[a、b用户相互关注]
     */
    private Integer relation = 0;
}
