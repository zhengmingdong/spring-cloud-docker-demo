package com.zynn.common.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * @author 袁毅雄
 * @description 用户关系
 * @date 2019/3/22
 */
@Data
public class UserRelationDTO {

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("明确关系用，不做它用")
    private final String describe = "A用户";

    @ApiModelProperty("当前用户关注数")
    private Integer attentionCount;

    @ApiModelProperty("当前用户粉丝数")
    private Integer fansCount;

    @ApiModelProperty("用户关系:key-关联用户id/value-关联用户")
    private Map<Long, RelatedUserDTO> userRelation;

    /**
     * 关系类型
     */
    public enum RelationshipType {

        A_FOCUS_ON_B(1, "A focus on B", "a用户单向关注b用户"),

        B_FOCUS_ON_A(2, "b focus on a", "b用户单向关注a用户"),

        MUTUAL_HAVE_RELATIONSHIP(3, "mutual have relationship", "a、b用户相互关注"),

        MUTUAL_NOT_RELATIONSHIP(0, "mutual not relationship", "a、b毫无关系");

        private Integer key;

        private String value;

        private String describe;


        RelationshipType(Integer key, String value, String describe) {
            this.key = key;
            this.value = value;
            this.describe = describe;
        }

        public Integer getKey() {
            return key;
        }

        @Override
        public String toString() {
            return Objects.toString(this.key);
        }
    }

}
