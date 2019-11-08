package com.zynn.service.module.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zynn.common.pojo.dto.user.UserAttentionDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 袁毅雄
 * @description 我的：访客、粉丝、关注信息
 * @date 2019/4/9
 */
@Data
@EqualsAndHashCode
public class UserVO extends UserAttentionDTO {

    /**
     * 访客(访问时间)、粉丝(被关注时间)、关注(关注时间)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
}
