package com.zynn.common.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 动态照片表VO
 * </p>
 *
 * @author wangyulin
 * @since 2019-03-13
 */
@Data
public class BsEventResourceVO {

    private static final long serialVersionUID = 1L;

    private Long Id;
    /**
     * 上传照片的用户id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long userId;
    /**
     * 上传照片所属动态的id
     */
    @JsonSerialize(using=ToStringSerializer.class)
    private Long eventId;
    /**
     * 动态资源类型,0照片,1文字,2地点,3短视频
     */
    private Integer resourceType;
    /**
     * 资源路径地址
     */
    private String resourceUrl;
    /**
     * 图片的高度
     */
    private Integer pictureHeight;
    /**
     * 图片的宽度
     */
    private Integer pictureWeight;
    /**
     * 图片的拍摄地理位置
     */
    private String pictureLocation;
    /**
     * 上传时间
     */
    @JsonIgnore
    private Date uploadTime;
    /**
     * 是否有效,1有效,0无效
     */
    private Integer isValid;
    /**
     * 视频的封面地址
     */
    private String coverPicUrl;
    /**
     * 播放时长(秒为单位)
     */
    private Integer playSecond;
    /**
     * 缩略显示的动态图
     */
    @TableField(exist = false)
    private String showEventPic;

    /**
     * 分享的动态图
     */
    @TableField(exist = false)
    private String sharePic;

    /**
     * 图片鉴定后的状态
     */
    @TableField(exist = false)
    private Integer pictureBlank;

}
