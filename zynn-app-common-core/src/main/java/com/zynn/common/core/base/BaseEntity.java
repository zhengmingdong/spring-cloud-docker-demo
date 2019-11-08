package com.zynn.common.core.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 公共的entity
 *
 * @author yu_chen
 * @date 2017-12-01 17:15
 **/
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = BaseEntity.ID)
    private Long id;

    @JsonIgnore
    @ApiModelProperty(value = "创建时间")
    @TableField(BaseEntity.CREATE_TIME)
    private Timestamp createTime;

    @JsonIgnore
    @ApiModelProperty(value = "修改时间")
    @TableField(value = BaseEntity.UPDATE_TIME, update = "now()")
    private Timestamp updateTime;

    @ApiModelProperty(value = "创建人")
    @TableField(BaseEntity.CREATE_USER)
    private String createUser;

    @ApiModelProperty(value = "修改人")
    @TableField(BaseEntity.UPDATE_USER)
    private String updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public final static String ID = "id";
    public final static String CREATE_USER = "create_user";
    public final static String CREATE_TIME = "create_time";
    public final static String UPDATE_USER = "update_user";
    public final static String UPDATE_TIME = "update_time";
}
