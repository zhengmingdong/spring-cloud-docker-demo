package com.zynn.common.pojo.dto.user;

import lombok.Data;

import java.util.List;

/**
 * @author 李琦
 *
 * 首页头部可能认识的人
 */
@Data
public class HomeHeadRcmdFriendDTO {

    /**
     * 用户id和头像集合
     */
    List<MayKnowFriendDTO> userInfo;

    /**
     * 可能认识人的数量
     */
    int num;

    /**
     * 取分值最高的用户昵称
     */
    private String nickName;
}
