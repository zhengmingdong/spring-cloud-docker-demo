package com.zynn.common.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author 袁毅雄
 * @description 推送的设置
 * @date 2019/3/28
 */
@Data
public class SettingPushDTO {

    public interface One {
    }

    @ApiModelProperty(value = "发送动态推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{sendEvent.empty}", groups = {SettingPushDTO.One.class})
    private Integer sendEvent = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "想去动态推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{wantEvent.empty}", groups = {SettingPushDTO.One.class})
    private Integer wantEvent = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "转发动态推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{transmitEvent.empty}", groups = {SettingPushDTO.One.class})
    private Integer transmitEvent = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "点赞动态推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{likeEvent.empty}", groups = {SettingPushDTO.One.class})
    private Integer likeEvent = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "评论动态推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{commentEvent.empty}", groups = {SettingPushDTO.One.class})
    private Integer commentEvent = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "回复动态推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{replyCommentEvent.empty}", groups = {SettingPushDTO.One.class})
    private Integer replyCommentEvent = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "关注推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{attention.empty}", groups = {SettingPushDTO.One.class})
    private Integer attention = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "取消关注推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{cancelAttention.empty}", groups = {SettingPushDTO.One.class})
    private Integer cancelAttention = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "聊天推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{chitchat.empty}", groups = {SettingPushDTO.One.class})
    private Integer chitchat = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    @ApiModelProperty(value = "好友注册推送:0-关闭、1-开启、默认开启推送")
    @NotNull(message = "{friendRegister.empty}", groups = {SettingPushDTO.One.class})
    private Integer friendRegister = Integer.valueOf(SettingPushType.OPEN_PUSH.toString());

    /**
     * 推送的状态枚举
     */
    public enum SettingPushType {

        OPEN_PUSH(1, "开启推送", "开启推送"),
        COLSE_PUSH(0, "开启推送", "开启推送");

        private Integer key;

        private String value;

        private String describe;

        SettingPushType(Integer key, String value, String describe) {
            this.key = key;
            this.value = value;
            this.describe = describe;
        }

        @Override
        public String toString() {
            return Objects.toString(this.key);
        }
    }
}
