package com.zynn.common.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 袁毅雄
 * @description 关联用户
 * @date 2019/3/22
 */
@Data
public class RelatedUserDTO {

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("明确关系用，不做它用")
    private final String describe = "B用户";

    @ApiModelProperty("当前用户关注数")
    private Integer attentionCount;

    @ApiModelProperty("当前用户粉丝数")
    private Integer fansCount;

    @ApiModelProperty("A用户与B用户的关系")
    private UserRelationDTO.RelationshipType relationshipType;

}
