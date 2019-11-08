package com.zynn.service.module.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zynn.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * <p>
 * 访问记录表
 * </p>
 *
 * @author zhengmingdong
 * @since 2019-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserVisitorInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using=ToStringSerializer.class)
    @ApiModelProperty(value = "访问用户")
    private Long visitUser;

    @JsonSerialize(using=ToStringSerializer.class)
    @ApiModelProperty(value = "被访问用户")
    private Long beVisitedUser;

    @ApiModelProperty(value = "访问时间")
    private Timestamp visitTime;

    @ApiModelProperty(value = "访客类型(0:真实访客，1：非真实访客—我的关注和我的粉丝，2：非真实访客—给用户推荐的可能认识的人，3：非真实访客—假数据（异性推荐）)")
    private Integer userType;

    @ApiModelProperty(value = "是否有效:有效-1;无效-0")
    private Integer isValid;


}
