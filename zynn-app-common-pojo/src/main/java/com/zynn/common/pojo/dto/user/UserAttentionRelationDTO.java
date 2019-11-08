package com.zynn.common.pojo.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <p>
 *  用户关注关系DTO
 * </p>
 *
 * @author wangyulin
 * @since 2019年3月20日19:54:13
 */
@Data
public class UserAttentionRelationDTO  {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 关注者用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long attentionUserId;
    /**
     * 被关注人用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long beAttentionUserId;
    /**
     * 0单向关注；1双向关注
     */
    private Integer allRelation;
    /**
     * 是否有效，1有效；0无效
     */
    private Integer isValid;
    /**
     * 创建时间
     */
    @JsonIgnore
    private Timestamp createTime;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Timestamp updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

}
