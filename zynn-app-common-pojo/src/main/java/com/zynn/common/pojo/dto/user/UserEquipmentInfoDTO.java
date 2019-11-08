package com.zynn.common.pojo.dto.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 袁毅雄
 * @description 用户设备信息
 * @date 2019/3/19
 */
@Data
public class UserEquipmentInfoDTO {

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id")
    private Long id;

    /**
     * 用户Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 用户登录的设备:Android,iOS
     */
    private String device;

    /**
     * 用户所用设备的机器码
     */
    private String deviceId;

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
