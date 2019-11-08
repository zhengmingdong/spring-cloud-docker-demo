package com.zynn.common.pojo.enums;

/**
 * @author yu_chen
 * @date 2018/5/17 14:43
 */
public enum BaseEnum {

    /**
     * 是否是管理员(管理员)
     */
    IS_MANAGER_YES(1, "是否是管理员(管理员)"),
    /**
     * 是否是管理员(普通成员)
     */
    IS_MANAGER_NO(0, "是否是管理员(普通成员)"),
    /**
     * 是否加入黑名单(加入)
     */
    IS_IN_BLACK_LIST_YES(1, "是否加入黑名单(加入)"),
    /**
     * 是否加入黑名单(没有加入)
     */
    IS_IN_BLACK_LIST_NO(0, "是否加入黑名单(没有加入)"),
    /**
     * 是否助力(已助力)
     */
    IS_LIKE_YES(1, "是否助力(已助力)"),
    /**
     * 是否助力(未助力)
     */
    IS_LIKE_NO(0, "是否助力(未助力)"),
    /**
     * 是否拥有(拥有)
     */
    IS_POSSESS_YES(1, "是否拥有(拥有)"),
    /**
     * 是否拥有(没有)
     */
    IS_POSSESS_NO(0, "是否拥有(没有)"),
    /**
     * 是否屏蔽(未屏蔽)
     */
    IS_SHIELD_YES(1, "是否屏蔽(未屏蔽)"),
    /**
     * 是否屏蔽(已屏蔽)
     */
    IS_SHIELD_NO(0, "是否屏蔽(已屏蔽)"),
    /**
     * 是否有效(有效)
     */
    IS_VALID_YES(1, "是否有效(有效)"),
    /**
     * 是否有效(无效)
     */
    IS_VALID_NO(0, "是否有效(无效)"),
    /**
     * 是否推广(推广)
     */
    IS_GENERALIZE_YES(1, "是否推广(推广)"),
    /**
     * 是否推广(未推广)
     */
    IS_GENERALIZE_NO(0, "是否推广(未推广)"),
    /**
     * 是否上架(未上架)
     */
    IS_SHELF_NO(0, "是否上架(未上架)"),
    /**
     * 是否上架(已上架)
     */
    IS_SHELF_YES(1, "是否上架(已上架)"),
    /**
     * 是否发布(未发布)
     */
    IS_PUBLISH_NO(0, "是否发布(未发布)"),
    /**
     * 是否发布(已发布)
     */
    IS_PUBLISH_YES(1, "是否发布(已发布)"),
    /**
     * 是否开放(未开放)
     */
    IS_OPEN_NO(0, "是否开放(未开放)"),
    /**
     * 是否开放(已开放)
     */
    IS_OPEN_YES(1, "是否开放(已开放)"),
    /**
     * 是否默认(不是默认)
     */
    IS_DEF_NO(0, "是否默认(不是默认)"),
    /**
     * 是否默认(是默认)
     */
    IS_DEF_YES(1, "是否默认(是默认)"),
    /**
     * 是否热门(不是热门)
     */
    IS_POPULAR_NO(0, "是否热门(不是热门)"),
    /**
     * 是否热门(是热门)
     */
    IS_POPULAR_YES(1, "是否热门(是热门)"),
    /**
     * 活动任务-浏览动态
     */
    ACTIVITY_BROWSE(1, "活动任务-浏览动态"),
    /**
     * 活动任务-评论动态
     */
    ACTIVITY_COMMENT(2, "活动任务-评论动态"),
    /**
     * 活动任务-点赞动态
     */
    ACTIVITY_LIKE(3, "活动任务-点赞动态"),
    /**
     * 活动任务-未完成
     */
    ACTIVITY_IS_FULFILL_NO(0, "活动任务-未完成"),
    /**
     * 活动任务-完成
     */
    ACTIVITY_IS_FULFILL_YES(1, "活动任务-完成"),
    /**
     * 是否置顶(置顶)
     */
    IS_TOP_YES(1, "是否置顶(置顶)"),
    /**
     * 是否置顶(不置顶)
     */
    IS_TOP_NO(0, "是否置顶(不置顶)"),
    /**
     * 是否删除(未删除)
     */
    IS_DELETED_NO(0, "是否删除(未删除)"),
    /**
     * 是否删除(已删除)
     */
    IS_DELETED_YES(1, "是否删除(已删除)"),
    /**
     * 是否加精(加精)
     */
    IS_ADD_COIN_YES(1, "是否加精(加精)"),
    /**
     * 是否置顶(不置顶)
     */
    IS_ADD_COIN_NO(0, "是否加精(不加精)"),

    /**
     * 是否管理员(是）
     */
    IS_ADMIN_YES(1, "是否管理员(是)"),
    /**
     * 是否管理员（否）
     */
    IS_ADMIN_NO(0, "是否管理员(否)"),
    /**
     * 是否能添加照片(是)
     */
    IS_CAN_PUBLISH_YES(1, "是否能添加照片(是)"),
    /**
     * 是否能添加照片否
     */
    IS_CAN_PUBLISH_NO(0, "是否能添加照片(否)"),
    /**
     * 用户关系-相互关注
     */
    EACH_OTHER_ATTENTION(3, "相互关注"),
    /**
     * 用户关系-我未关注对方但是对方关注了我
     */
    ONLY_OTHER_ATTENTION(2, "我未关注对方但是对方关注了我"),
    /**
     * 用户关系-我关注了对方但是对方未关注我
     */
    ONLY_ME_ATTENTION(1, "我关注了对方但是对方未关注我"),
    /**
     * 用户关系-无任何关注关注关系
     */
    NOT_ANY_ATTENTION(0, "无任何关注关注关系"),

    /**
     * 相册来源pugc相册
     */
    PUGC_GOLD_TYPE(1,"PUGC相册"),

    /**
     * 养成金币来源
     */
    PLANT_GOLD_TYPE(2,"养成");

    private Integer key;
    private String description;

    BaseEnum(Integer key, String description) {
        this.key = key;
        this.description = description;
    }

    public Integer getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
