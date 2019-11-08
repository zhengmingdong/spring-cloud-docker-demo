package com.zynn.common.core.constant;

/**
 * @author 刘天元
 * @description 键值前缀
 * @create 2018-06-22 16:14
 **/
public class RedisKeyConstants {

    /**
     * 登陆 信息前缀
     */
    public static final String REDIS_PREFIX = "app";

    /**
     * 雪花算法对应的机器id
     */
    public static final String SERVICE_MACHINE_ID = "SERVICE_MACHINE_ID";

    /**
     * 敏感词
     */
    public static final String APP_SENSITIVITY_WORD = "APP_SENSITIVITY_WORD";

    /**
     * 手机号跳过标志
     */
    public static final String APP_SKIP_BLANK = "APP_SKIP_BLANK";

    /**
     * 环信token
     */
    public static final String HUANXIN_TOKEN = "huanxin_token";

    /**
     * 用户信息key，用户保存用户相关的信息
     */
    public static final String USER_INFO = "user_info";

    /**
     * 用户环信信息
     */
    public static final String USER_HUANXIN_INFO = "user_huanxin_info";

    /**
     * 用户基础信息
     */
    public static final String USER_BASE_INFO = "user_base_info";

    /**
     * 首页地图信息缓存
     */
    public static final String MAP_LOCATION_POINT = "map_location_point";

    /**
     * 获取校园匿名名称
     */
    public static final String GET_SCHOOL_ANONYMITY_NAME = "GET_SCHOOL_ANONYMITY_NAME_NEW";

    /**
     * 地点领主图
     */
    public final static String FEUDAL_LORD_IMAGE = "FEUDAL_LORD_IMAGE";

    /**
     * 个推未读推送计数普通
     */
    public final static String GE_TUI_UNREAD_PUST_COUNT = "GE_TUI_UNREAD_PUST_COUNT";

    /**
     * 个推未读推送计数说说
     */
    public final static String GE_TUI_UNREAD_PUST_COUNT_TALK = "GE_TUI_UNREAD_PUST_COUNT_TALK";

    /**
     * 个推推送控制:统计用户每天收到的所有推送数
     */
    public final static String GE_TUI_PUST_EVERYDAY_CONTROL = "GE_TUI_PUST_EVERYDAY_CONTROL";

    /**
     * 校园说新增数量
     */
    public final static String SCHOOL_ANONYMITY_TOPIC_EVENT_NEW_COUNT = "SCHOOL_ANONYMITY_TOPIC_EVENT_NEW_COUNT";

    /**
     * 推送相关常量
     */
    public static class PushConstants {

        /**
         * 系统推送
         */
        public static class SystemPushConstants {
            /**
             * 个推推送控制 前缀
             */
            public final static String GE_TUI_EVERYDAY = "GE_TUI_EVERYDAY";

            /**
             * 个推推送控制:记录用户每天收到的关注
             */
            public final static String GE_TUI_EVERYDAY_USER_ATTENTION = GE_TUI_EVERYDAY + "_USER_ATTENTION";

            /**
             * 个推推送控制:记录用户每天收到的好友注册
             */
            public final static String GE_TUI_EVERYDAY_FRIEND_REGISTER = GE_TUI_EVERYDAY + "_FRIEND_REGISTER";

            /**
             * 个推推送控制:记录用户每天用户访问
             */
            public final static String GE_TUI_EVERYDAY_USER_VISIT = GE_TUI_EVERYDAY + "_USER_VISIT";

            /**
             * 个推推送控制:记录用户每天好友发送动态
             */
            public final static String GE_TUI_EVERYDAY_FRIEND_SEND_EVENT = GE_TUI_EVERYDAY + "_FRIEND_SEND_EVENT";

            /**
             * 个推推送控制:记录用户每天超级KOL发送动态
             */
            public final static String GE_TUI_EVERYDAY_SUPER_KOL_SEND_EVENT = GE_TUI_EVERYDAY + "_SUPER_KOL_SEND_EVENT";

            /**
             * 个推推送控制:记录用户每天好友加入圈子
             */
            public final static String GE_TUI_EVERYDAY_ATTENTION_FRIEND_JOIN_CIRCLE = GE_TUI_EVERYDAY + "_ATTENTION_FRIEND_JOIN_CIRCLE";

            /**
             * 个推推送控制:记录用户每天收到的好友注册
             */
            public final static String GE_TUI_EVERYDAY_ACTIVITY_INVITE_REGISTER = GE_TUI_EVERYDAY + "_ACTIVITY_INVITE_REGISTER";

            /**
             * 个推推送控制:记录用户每天好友注册次数
             */
            public static final String GE_TUI_EVERYDAY_ACTIVITY_INVITE_REGISTER_COUNT = GE_TUI_EVERYDAY_ACTIVITY_INVITE_REGISTER + "_CONUT";

            /**
             * 个推推送控制:记录用户每天收到的评论
             */
            public final static String GE_TUI_EVERYDAY_COMMENT = GE_TUI_EVERYDAY + "_COMMENT";

            /**
             * 个推推送控制:记录用户每天收到的评论回复
             */
            public final static String GE_TUI_EVERYDAY_REPLY_COMMENT = GE_TUI_EVERYDAY + "_REPLY_COMMENT";

            /**
             * 个推推送控制:记录用户每天收到的点赞
             */
            public final static String GE_TUI_EVERYDAY_LIKE = GE_TUI_EVERYDAY + "_LIKE";

            /**
             * 个推推送控制:记录用户每天收到的想去
             */
            public final static String GE_TUI_EVERYDAY_WANT = GE_TUI_EVERYDAY + "_WANT";

            /**
             * 个推推送控制:记录用户每天收到的转发
             */
            public final static String GE_TUI_EVERYDAY_TRANSMIT = GE_TUI_EVERYDAY + "_TRANSMIT";

            /**
             * 个推推送控制:记录用户每天收到的发送@
             */
            public final static String GE_TUI_EVERYDAY_SEND = GE_TUI_EVERYDAY + "_SEND";
        }

        /**
         * 特殊推送
         */
        public static class SpecialPushConstants {

            /**
             * 新的可能认识的用户
             */
            public final static String NEW_POSSIBLE_KNOW_USER = "NEW_POSSIBLE_KNOW_USER";
            /**
             * 自主推送用户ID
             */
            public final static String AUTONOMOUSLY_PUSH_INFO = "AUTONOMOUSLY_PUSH_INFO";
            /**
             * 用户来到新的城市
             */
            public final static String USER_COME_NEW_CITY = "USER_COME_NEW_CITY";

            /**
             * 新心愿单动态推送
             */
            public final static String NEWLY_EVENT_WISH_PUSH = "NEWLY_EVENT_WISH_PUSH";

            /**
             * 用户地点信息
             **/
            public static final String USER_SITE_INFO = "USER_SITE_INFO";

            /**
             * 用户相册地点列表
             */
            public static final String USER_ALBUM_POSITION_LIST = "USER_ALBUM_POSITION_LIST";

            /**
             * 我的学校增加了多少人
             */
            public static final String NEWLY_SCHOOLMATE = "NEWLY_SCHOOLMATE";

            /**
             * 我的互关好友关注了超级kol
             */
            public static final String FRIEND_NEWLY_ATTENTION_KOL = "FRIEND_NEWLY_ATTENTION_KOL";

            /**
             * 我关注的好友评论了我评论/点赞过的内容
             */
            public static final String FRIEND_COMMENT_RELATED_TO_MY = "FRIEND_COMMENT_RELATED_TO_MY";

            /**
             * 我关注的好友点赞了我评论/点赞过的内容
             */
            public static final String FRIEND_LIKE_RELATED_TO_MY = "FRIEND_LIKE_RELATED_TO_MY";
        }
    }


    /**
     * 访客的聚合数据:关注、粉丝、访客
     */
    public final static String USER_AGGREGATION_INFO = "USER_AGGREGATION_INFO";

    /**
     * 关注好友新卡片
     */
    public final static String USER_AGGREGATION_CARD = "USER_AGGREGATION_CARD";

    /**
     * 设置推送
     */
    public final static String SETTING_PUSH = "SETTING_PUSH";

    /**
     * 粉丝数
     */
    public final static String NEWLY_FANS_NUMBER = "NEWLY_FANS_NUMBER";

    /**
     * 点赞数
     */
    public final static String NEWLY_ATTENTIONS_NUMBER = "NEWLY_ATTENTIONS_NUMBER";

    /**
     * 访客数
     */
    public final static String NEWLY_VISITOR_NUMBER = "NEWLY_VISITOR_NUMBER";

    /**
     * 位置信息
     */
    public final static String NEW_EVENT_POSITION = "new_event_position_%s_%s_%s_%s";

    /**
     * 位置信息
     */
    public final static String USER_INVITATION_CODE = "USER_INVITATION_CODE_%s";

    /**
     * 第一次注册标志
     */
    public final static String FIRST_REGISTER_BLANK = "FIRST_REGISTER_BLANK";

    /**
     * 地点详情顶部动态图
     */
    public final static String LOCATION_EVENT_TOP_LIST = "LOCATION_EVENT_TOP_LIST";

    /**
     * 姓氏列表
     */
    public final static String SURNAME_LIST = "surname_list";

    /**
     * 城市对应的cityId
     */
    public final static String NEW_CITY = "NEW_CITY";

    /**
     * 用户真实名称
     */
    public static final String USER_REAL_NAME = "user_real_name";

    /**
     * 抽奖活动锁
     */
    public static final String ACTIVITY_DRAW_LOCK = "ACTIVITY_DRAW_LOCK";

    /**
     * 抽奖锁
     */
    public static final String ACTIVITY_DRAW_RANKING_PROBABILITY_LOCK = "ACTIVITY_DRAW_RANKING_PROBABILITY_LOCK";

    /**
     * 抽奖锁
     */
    public static final String ACTIVITY_DRAW_PLOG_LOCK = "ACTIVITY_DRAW_PLOG_LOCK";

    /**
     * 名次抽奖 奖品
     */
    public static final String ACTIVITY_DRAW_REGISTER_RANKING_PRIZE = "ACTIVITY_DRAW_REGISTER_RANKING_PRIZE";

    /**
     * 名次抽奖 排名
     */
    public static final String ACTIVITY_DRAW_REGISTER_RANKING = "ACTIVITY_DRAW_REGISTER_RANKING";

    /**
     * 概率抽奖 奖品
     */
    public static final String ACTIVITY_DRAW_REGISTER_PROBABILITY_PRIZE = "ACTIVITY_DRAW_REGISTER_PROBABILITY_PRIZE";

    /**
     * weixin code
     */
    public static final String ORIGINAL_URL_LONG_LINKS = "ORIGINAL_URL_LONG_LINKS";

    /**
     * 用户卡片
     */
    public static final String USER_CARD_JOIN = "USER_CARD_JOIN";

    /**
     * 用户好有请求卡片
     */
    public static final String USER_CARD_ATTENTION = "USER_CARD_ATTENTION";

    /**
     * 微信code
     */
    public static final String WECHAT_CODE = "WECHAT_CODE";

    /**
     * 用户好友请求阅读时间
     */
    public static final String USER_DEMAND_READ_INFO = "user_demand_read_info";

    /**
     * 上传动态时用户资源的hash码
     */
    public static final String UPLOAD_EVENT_PIC_HASH = "UPLOAD_EVENT_PIC_HASH";

    /**
     * 活动期间用户任务
     */
    public static final String ACTIVITY_USER_TASK = "ACTIVITY_USER_TASK";

    /**
     * 活动期间用户先进入的任务，再进入的活动主页
     */
    public static final String ACTIVITY_USER_FIRST_ENTRANCE_TASK = "ACTIVITY_USER_FIRST_ENTRANCE_TASK";

    /**
     * 活动期间用户先进入任务后是否第一次进入主页，只有第一次进入主页才会增加一次抽奖机会
     */
    public static final String ACTIVITY_USER_ENTRANCE_HOME_PAGE = "ACTIVITY_USER_ENTRANCE_HOME_PAGE";

    /**
     * 活动期间，抽奖主页是否显示邀请码填写窗口
     */
    public static final String ACTIVITY_DRAW_HOME_PAGE_INVITE_CODE_IS_SHOW = "ACTIVITY_DRAW_HOME_PAGE_INVITE_CODE_IS_SHOW";

    /**
     * 活动期间，进入过活动主页再关注才有效
     */
    public static final String ACTIVITY_ATTENTION_IS_CALCULATE = "ACTIVITY_ATTENTION_IS_CALCULATE";

    /**
     * 新好友加入我未进入的圈子
     */
    public static final String NEW_FRIEND_ADD_CLUSTER = "NEW_FRIEND_ADD_CLUSTER";

    /**
     * 活动抽奖排行榜
     */
    public static final String ACTIVITY_AWARD_PRIZE_DRAW_RANKING = "ACTIVITY_AWARD_PRIZE_DRAW_RANKING";
}
