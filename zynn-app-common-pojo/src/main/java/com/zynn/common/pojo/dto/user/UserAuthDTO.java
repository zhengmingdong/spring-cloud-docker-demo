package com.zynn.common.pojo.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author zhanghao
 * @date 2019/4/26 16:19
 **/
@Data
public class UserAuthDTO {

    private Long userId;

    private Long cellphone;

    private String weiXinUnionId;

    private String weiXinOpenId;

    private String qQUnionId;

    private String qQOpenId;

    private String registerSource;
}
