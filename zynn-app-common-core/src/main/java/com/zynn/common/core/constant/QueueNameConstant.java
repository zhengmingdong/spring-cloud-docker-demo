package com.zynn.common.core.constant;

/**
 * @ClassName DiscoveryQueueNameConstant
 * @Description TODO
 * @Author wangyulin
 * @Date 2019年3月8日15:24:52
 **/
public class QueueNameConstant {

    /**
     * 监听后缀大写
     */
    public static final String INPUT_SUFFIX_UPPER = "_INPUT";

    /**
     * 写入后缀大写
     */
    public static final String OUTPUT_SUFFIX_UPPER = "_OUTPUT";
    /**
     * 监听后缀小写
     */
    public static final String INPUT_SUFFIX_LOWER = "_input";

    /**
     * 写入后缀小写
     */
    public static final String OUTPUT_SUFFIX_LOWER = "_output";

    /**
     * 用户注册补充信息-同步用户信息至ES
     */
    public static final String USER_SYNC_INFO_ES = "USER_SYNC_INFO_ES";

    /**
     * 用户环信注册，用于在失败之后进行重试
     */
    public static final String USER_HUANXIN_REGISTER = "user_huanxin_register";

    /**
     * 环信聊天消息记录查询，失败之后重试
     */
    public static final String HUANXIN_CHAT_MESSAGE_RECORD = "huanxin_chat_message_query";

    /**
     * 新增地理位置信息到es队列名
     */
    public static final String INSERT_POSITION_TO_ES = "INSERT_POSITION_TO_ES";

    /**
     * 更新地理位置信息到es队列名
     */
    public static final String UPDATE_POSITION_TO_ES = "UPDATE_POSITION_TO_ES";

    /**
     * 新增动态信息到es队列名
     */
    public static final String INSERT_EVENT_TO_ES = "INSERT_EVENT_TO_ES";

    /**
     * 新增点赞信息到es队列名
     */
    public static final String INSERT_LIKE_TO_ES = "INSERT_LIKE_TO_ES";

    /**
     * 修复点赞信息到es队列名
     */
    public static final String REPAIR_LIKE_TO_ES = "REPAIR_LIKE_TO_ES";

    /**
     * 修复点赞信息到es队列名
     */
    public static final String SYNCHRONIZE_PLOG_INFO = "SYNCHRONIZE_PLOG_INFO";

    /**
     * 更新位置索引的想去数量到es队列名
     */
    public static final String UPDATE_POSITION_WANT_TO_ES = "UPDATE_POSITION_WANT_TO_ES";

    /**
     * 保存用户通讯录
     */
    public static final String SAVE_USER_ADDRESS = "SAVE_USER_ADDRESS";

    /**
     * 一个用户关注多个用户
     */
    public static final String SAVE_BATCH_ATTENTION = "SAVE_BATCH_ATTENTION";

    /**
     * 多个用户关注一个用户
     */
    public static final String MANY_SAVE_BATCH_ATTENTION = "MANY_SAVE_BATCH_ATTENTION";

    /**
     * 短信邀请批量关注(正反向)
     */
    public static final String SMS_SAVE_BATCH_ATTENTION_TO = "SMS_SAVE_BATCH_ATTENTION_TO";

    /**
     * 微信邀请批量关注(正反向)
     */
    public static final String WECHAT_SAVE_BATCH_ATTENTION_TO = "WECHAT_SAVE_BATCH_ATTENTION_TO";

    /**
     * 同步用户通讯录至Es
     */
    public static final String SAVE_USER_ADDRESS_TO_ES = "SAVE_USER_ADDRESS_TO_ES";

    /**
     * 推送数据保存
     */
    public static final String PUSH_DATA_SAVE = "push_data_save";

    /**
     * 推送数据更新状态
     */
    public static final String PUSH_DATA_UPDATE_STATUS = "push_data_update_status";

    /**
     * 对用户上传的图片进行七牛云鉴定
     */
    public static final String AUTHENTICATE_UPLOAD_PICTURE = "AUTHENTICATE_UPLOAD_PICTURE";

    /**
     * 发送邀请下载短信
     */
    public static final String SEND_DOWNLOAD_URL_SMS = "SEND_DOWNLOAD_URL_SMS";

    /**
     * 批量发送邀请下载短信
     */
    public static final String SEND_DOWNLOAD_URL_SMS_BATCH = "SEND_DOWNLOAD_URL_SMS_BATCH";

    /**
     * 生成地点/位置动态默认图片
     */
    public static final String CREATE_EVENT_DEFAULT_PICTURE = "CREATE_EVENT_DEFAULT_PICTURE";

    /**
     * 从redis中删除地理位置缓存
     */
    public static final String DELETE_POSITION_FROM_REDIS = "DELETE_POSITION_FROM_REDIS";

    /**
     * 更新动态的城市id
     */
    public static final String UPDATE_EVENT_CITY_INFO = "UPDATE_EVENT_CITY_INFO";

    /**
     * 好友注册
     */
    public static final String PRODUCE_FRIEND_REGISTER = "PRODUCE_FRIEND_REGISTER";


    /**
     * 校园匿名昵称分配
     */
    public static final String SCHOOL_ANONYMITY_NAME = "SCHOOL_ANONYMITY_NAME";

    /**
     * 删除动态
     */
    public static final String DELETE_EVENT = "DELETE_EVENT";

    /**
     * 批量加入圈子
     */
    public static final String BATCH_ADD_CLUSTER = "BATCH_ADD_CLUSTER";



    public static class Push {
        /***
         * 系统推送
         */
        public static class SystemPush {
            /**
             * 发送动态事件
             */
            public static final String SEND_EVENT = "SEND_EVENT";

            /**
             * 想去
             */
            public static final String WANT_EVENT = "WANT_EVENT";

            /**
             * 转发
             */
            public static final String TRANSMIT_EVENT = "TRANSMIT_EVENT";

            /**
             * 点赞动态事件
             */
            public static final String LIKE_EVENT = "LIKE_EVENT";

            /**
             * 评论动态事件
             */
            public static final String COMMENT_EVENT = "COMMENT_EVENT";


            /**
             * 回复评论动态事件
             */
            public static final String REPLY_COMMENT_EVENT = "REPLY_COMMENT_EVENT";


            /**
             * 关注用户事件
             */
            public static final String USER_ATTENTION = "USER_ATTENTION";

            /**
             * 用户访问
             */
            public static final String USER_VISIT = "USER_VISIT";

            /**
             * 好友注册
             */
            public static final String FRIEND_REGISTER = "FRIEND_REGISTER";

            /**
             * 好友发送动态
             */
            public static final String FRIEND_SEND_EVENT = "FRIEND_SEND_EVENT";

            /**
             * 超级KOL
             */
            public static final String SUPER_KOL_SEND_EVENT = "SUPER_KOL_SEND_EVENT";

            /**
             * 活动邀请注册
             */
            public static final String ACTIVITY_INVITE_REGISTER = "ACTIVITY_INVITE_REGISTER";

            /**
             * 关注的好友加入了圈子
             */
            public static final String ATTENTION_FRIEND_JOIN_CIRCLE = "ATTENTION_FRIEND_JOIN_CIRCLE";
        }

        /**
         * 特殊推送
         */
        public static class SpecialPush {
            /**
             * 我关注的好友评论了我评论/点赞过的内容
             */
            public static final String FRIEND_COMMENT_RELATED_TO_MY = "FRIEND_COMMENT_RELATED_TO_MY";

            /**
             * 我关注的好友点赞了我评论/点赞过的内容
             */
            public static final String FRIEND_LIKE_RELATED_TO_MY = "FRIEND_LIKE_RELATED_TO_MY";

            /**
             * 我的互关好友关注了超级KOL
             */
            public static final String FRIEND_NEWLY_ATTENTION_KOL = "FRIEND_NEWLY_ATTENTION_KOL";

            /**
             * 我的学校增加了多少人
             */
            public static final String NEWLY_SCHOOLMATE = "NEWLY_SCHOOLMATE";

            /**
             * 新的可能认识的人
             */
            public static final String NEW_POSSIBLE_KNOW_USER = "NEW_POSSIBLE_KNOW_USER";

            /**
             * 关注的好友发布了想去动态
             */
            public static final String FRIEND_NEW_WANT_GO_EVENT = "FRIEND_NEW_WANT_GO_EVENT";

            /**
             * 发现新的地点照片
             */
            public static final String NEWLY_SITE_PHOTOGRAPH = "NEWLY_SITE_PHOTOGRAPH";

            /**
             * 朋友来到我的城市
             */
            public static final String FRIEND_COME_MY_CITY = "FRIEND_COME_MY_CITY";

            /**
             * 来到新的城市
             */
            public static final String COME_NEW_CITY = "COME_NEW_CITY";

            /**
             * 发现新的城市照片
             */
            public static final String NEWLY_CITY_PHOTOGRAPH = "NEWLY_CITY_PHOTOGRAPH";
        }
    }

    /**
     * 用户真实名称保存
     */
    public static final String USER_REAL_NAME = "user_real_name";

    /**
     * 用户活动邀请注册
     */
    public static final String USER_REGISTER_ACTIVITY_INVITE = "user_register_activity_invite";

    /**
     * 用户活动邀请注册
     */
    public static final String USER_REGISTER_ACTIVITY_CODE_INVITE = "user_register_activity_code_invite";

    /**
     * plog推广邀请码填入
     */
    public static final String AWARD_REGISTER_ACTIVITY_CODE_INVITE = "award_register_activity_code_invite";

    /**
     * 用户活动期间每日关注5人同校好友获取一次抽奖机会
     */
    public static final String USER_ATTENTION_DRAW_TIME = "USER_ATTENTION_DRAW_TIME";

    /**
     * 用户活动期间每日上传动态获取一次抽奖机会
     */
    public static final String ACTIVITY_UPLOAD_EVENT = "ACTIVITY_UPLOAD_EVENT";

    /**
     * 上传plog开启奖品池
     */
    public static final String ACTIVITY_UPLOAD_STORY_OPEN_GIFT_POOL = "ACTIVITY_UPLOAD_STORY_OPEN_GIFT_POOL";


    /**
     * 用户关注聊天消息
     */
    public static final String USER_ATTENTION_IM_MESSAGE = "user_attention_im_message";
}
