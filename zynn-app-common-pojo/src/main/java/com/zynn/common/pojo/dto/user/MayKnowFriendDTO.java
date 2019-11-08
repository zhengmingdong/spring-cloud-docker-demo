package com.zynn.common.pojo.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author liqi
 *
 * 可能认识的人
 */
@Data
public class MayKnowFriendDTO implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户头像
     */
    private String photo;
}
