package com.zynn.common.pojo.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 袁毅雄
 * @description
 * @date 2019/7/17
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KolUserDTO {

    /**
     * KOL用户ID
     */
    private Long userId;

    /**
     * KOL用户ID
     */
    private String userPic;

    /**
     * KOL类型
     */
    private Long kolTypeId;

    /**
     * KOL类型名称
     */
    private String kolTypeName;

    /**
     * KOL头部图片
     */
    private String kolTitleImage;

    /**
     * 用户性别:男:1;女:2
     */
    private Integer userSex;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 一周内动态的点赞评论总数
     */
    private Integer commentAndLikeCount;

    /**
     * 用户背景图
     */
    private String backgroundPic;

}
