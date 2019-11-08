package com.zynn.common.pojo.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 用户信息扩展表
 * </p>
 *
 * @author wangyulin
 * @since 2019-04-27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserExtendInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id")
    private Long id;

    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField("create_time")
    private Timestamp createTime;

    /**
     * 修改时间
     */
    @JsonIgnore
    @TableField("update_time")
    private Timestamp updateTime;


    /**
     * 用户背景图
     */
    private String backgroundPic;

    /**
     * 职位/兴趣特长
     */
    private String postInterest;

    /**
     * 是否有效，0无效，1有效
     */
    private Integer isValid;


}
