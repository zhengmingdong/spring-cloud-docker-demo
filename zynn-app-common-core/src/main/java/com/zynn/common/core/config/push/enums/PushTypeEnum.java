package com.zynn.common.core.config.push.enums;

/**
 * @author 袁毅雄
 * @description 推送类型
 * @date 2019/8/13
 */
public enum PushTypeEnum {

    /**
     * 发送动态
     */
    SEND_EVENT(1, "发送动态"),

    /**
     * 想去
     */
    WANT_EVENT(2, "想去"),

    /**
     * 转发
     */
    TRANSMIT_EVENT(3, "转发"),

    /**
     * 点赞动态
     */
    LIKE_EVENT(4, "点赞动态"),

    /**
     * 评论动态
     */
    COMMENT_EVENT(5, "评论动态"),

    /**
     * 回复评论动态
     */
    REPLY_COMMENT_EVENT(6, "回复评论动态"),

    /**
     * 用户关注
     */
    USER_ATTENTION(7, "用户关注"),

    /**
     * 用户访问
     */
    USER_VISIT(8, "用户访问"),

    /**
     * 好友注册
     */
    FRIEND_REGISTER(9, "好友注册"),

    /**
     * 好友发送动态
     */
    FRIEND_SEND_EVENT(10, "好友发送动态"),

    /**
     * 超级KOL
     */
    SUPER_KOL_SEND_EVENT(11, "超级KOL发送动态"),

    /**
     * 活动邀请注册
     */
    ACTIVITY_INVITE_REGISTER(12, "活动邀请注册"),

    /**
     * 关注的好友加入了圈子
     */
    ATTENTION_FRIEND_JOIN_CIRCLE(13, "关注的好友加入了圈子"),

    /**
     * 我关注的好友评论了我评论/点赞过的内容
     */
    FRIEND_COMMENT_RELATED_TO_MY(30, "我关注的好友评论了我评论/点赞过的内容"),

    /**
     * 我关注的好友点赞了我评论/点赞过的内容
     */
    FRIEND_LIKE_RELATED_TO_MY(31, "我关注的好友点赞了我评论/点赞过的内容"),

    /**
     * 我的互关好友关注了超级KOL
     */
    FRIEND_NEWLY_ATTENTION_KOL(32, "我的互关好友关注了超级KOL"),

    /**
     * 我的学校增加了多少人
     */
    NEWLY_SCHOOLMATE(33, "的学校增加了多少人"),

    /**
     * 新的可能认识的人
     */
    NEW_POSSIBLE_KNOW_USER(34, "新的可能认识的人"),

    /**
     * 关注的好友发布了想去动态
     */
    FRIEND_NEW_WANT_GO_EVENT(35, "关注的好友发布了想去动态"),

    /**
     * 发现新的地点照片
     */
    NEWLY_SITE_PHOTOGRAPH(36, "发现新的地点照片"),

    /**
     * 朋友来到我的城市
     */
    FRIEND_COME_MY_CITY(37, "朋友来到我的城市"),

    /**
     * 来到新的城市
     */
    COME_NEW_CITY(38, "来到新的城市"),

    /**
     * 发现新的城市照片
     */
    NEWLY_CITY_PHOTOGRAPH(39, "发现新的城市照片"),;

    private Integer key;

    private String describe;

    PushTypeEnum(Integer key, String describe) {
        this.key = key;
        this.describe = describe;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Integer getKey() {
        return key;
    }
}
