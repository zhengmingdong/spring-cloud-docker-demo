package com.zynn.common.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 动态
 *
 * @author 王宇林
 * @date 2019年3月14日15:05:17
 */
@Data
@Accessors(chain = true)
public class EventDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 发布动态的用户id
     */
    private Long userId;

    /**
     * 动态资源类型:0照片,1文字,2位置,3短视频
     */
    private Integer sourceType;

    /**
     * 动态展示类型:0普通动态,1心愿动态
     */
    private Integer showType;

    /**
     * 发布动态的文字内容
     */
    private String content;

    /**
     * 动态的首个展示资源的地址
     */
    private String firstSourceUrl;

    /**
     * 发布位置的经度
     */
    private Double longitude;

    /**
     * 发布位置的纬度
     */
    private Double latitude;

    /**
     * 经纬度
     */
    private String latitudeLongitude;

    /**
     * 发布的详细位置
     */
    private String location;

    /**
     * 位置的id
     */
    private Long positionId;

    /**
     * 城市id
     */
    private Long cityId;

    /**
     * 动态的话题Id
     */
    private String gambitId;

    /**
     * 是否置顶,1置顶,0不置顶
     */
    private Integer isTop;

    /**
     * 是否是KOL动态,1KOL,0非KOL
     */
    private Integer isKOL;

    /**
     * 是否有效,1有效,0无效
     */
    private Integer isValid;

    /**
     * 是否是KOL动态,1KOL,0非KOL
     */
    private Integer isKolEvent;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 发布时间
     */
    private String createTime;

    /**
     * 动态图片数量
     */
    private Integer picCount;

    /**
     * 地点打卡排名
     */
    private Integer positionRank;

    /**
     * 评论人用户id
     */
    private Long commentUserId;

    /**
     * 点赞标志
     */
    private Boolean likeBlank;

    /**
     * 圈子id
     */
    private Long clusterId;

    /**
     * 学校id
     */
    private Long schoolId;

    /**
     * 审批状态 0.未处理 1.待审核 2.已通过 3.已删除
     */
    public Integer approvalBlank;
}