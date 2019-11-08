package com.zynn.service.module.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 访问用户DTO
 * @author wangyulin
 * @date 2019年4月12日15:03:23
 */
@Data
public class VisitDTO {

    @NotNull(message = "{visitUserId.empty}")
    @ApiModelProperty("被访问用户Id")
    private Long visitUserId;

}
