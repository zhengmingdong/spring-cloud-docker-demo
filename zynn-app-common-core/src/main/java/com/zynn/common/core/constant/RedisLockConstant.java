package com.zynn.common.core.constant;

/**
 *  redis 锁 前缀
 *
 * @author 杨岳
 * @date 2018/8/16 17:09
 */
public class RedisLockConstant {

    //定时任务锁前缀
    public static final String TASK_LOCK_PREFIX = "task_lock";

    /**
     * 点赞数变化锁
     */
    public static final String LIKE_COUNT_LOCK = "LIKE_COUNT_LOCK";

    /**
     * 动态阅读
     */
    public static final String EVENT_READ = "EVENT_READ";

    /**
     * 评论数变化锁
     */
    public static final String COMMENT_COUNT_LOCK = "COMMENT_COUNT_LOCK";

    /**
     * 地点更新锁
     */
    public static final String POSITION_CHANGE_LOCK = "POSITION_CHANGE_LOCK";

}
