package com.zynn.common.core.constant;

/**
 * @author 袁毅雄
 * @description Es索引库
 * @date 2019/3/14
 */
public final class EsIndexConstant {

    /**
     * 地点索引
     */
    public static class PositionIndex {

        /**
         * 索引名称
         */
        public static final String INDEX_NAME = "position_index";

        /**
         * 索引类型
         */
        public static final String INDEX_TYPE = "_doc";

        /**
         * 索引属性
         */
        public static class Properties {

            /**
             * 地点id
             */
            public static final String POSITION_ID = "position_id";

            /**
             * 一级地点：国家
             */
            public static final String COUNTRY = "country";

            /**
             * 二级地点：省份
             */
            public static final String PROVINCE = "province";

            /**
             * 三级地点：城市
             */
            public static final String CITY = "city";

            /**
             * 四级地点：位置
             */
            public static final String POSITION = "position";

            /**
             * 地点完整相信信息
             */
            public static final String LOCATION = "location";

            /**
             * 经纬度
             */
            public static final String LATITUDE_LONGITUDE = "latitude_longitude";

            /**
             * 来过数[统计]
             */
            public static final String COME_COUNT = "come_count";

            /**
             * 想去数[统计]
             */
            public static final String WANT_COUNT = "want_count";

            /**
             * 地点排名
             */
            public static final String POSITION_RANK= "position_rank";

            /**
             * 地点名称,用于分词查询
             */
            public static final String POSITION_QUERY = "position_query";

        }
    }

    /**
     * 动态索引
     */
    public static class EventIndex {

        /**
         * 索引名称
         */
        public static final String INDEX_NAME = "event_index";

        /**
         * 索引类型
         */
        public static final String INDEX_TYPE = "_doc";

        /**
         * 索引属性
         */
        public static class Properties {

            /**
             * 评论数[统计]
             */
            public static final String COMMENT_COUNT = "comment_count";

            /**
             * 点赞数[统计]
             */
            public static final String LIKE_COUNT = "like_count";

            /**
             * 图片数[统计]
             */
            public static final String PIC_COUNT = "pic_count";

            /**
             * 创建时间
             */
            public static final String CREATE_TIME = "create_time";

            /**
             * 动态资源类型
             */
            public static final String SOURCE_TYPE = "source_type";

            /**
             * 经纬度
             */
            public static final String LATITUDE_LONGITUDE = "latitude_longitude";

            /**
             * 动态发送位置
             */
            public static final String LOCATION = "location";

            /**
             * 动态置顶标志
             */
            public static final String IS_TOP = "is_top";

            /**
             * 动态展示类型
             */
            public static final String SHOW_TYPE = "show_type";

            /**
             * 动态Id
             */
            public static final String EVENT_ID = "event_id";

            /**
             * 用户id
             */
            public static final String USER_ID = "user_id";

            /**
             * 是否有效
             */
            public static final String IS_VALID = "is_valid";

            /**
             * 话题Id
             */
            public static final String GAMBIT_ID = "gambit_id";

            /**
             * 是否为kol动态
             */
            public static final String IS_KOL = "is_kol";

            /**
             * 位置Id
             */
            public static final String POSITION_ID = "position_id";

            /**
             * 城市id
             */
            public static final String CITY_ID = "city_id";

            /**
             * 学校id
             */
            public static final String SCHOOL_ID = "school_id";

            /**
             * 地点打卡排名
             */
            public static final String POSITION_RANK = "position_rank";

            /**
             * 话题id,多个之间","分割
             */
            public static final String TOPIC_IDS = "topic_ids";

            /**
             * 主题id,多个之间","分割
             */
            public static final String SUBJECT_IDS = "subject_ids";

            /**
             * 片区id,多个之间","分割
             */
            public static final String PUNCH_IDS = "punch_ids";

            /**
             * 活动id
             */
            public static final String ACTIVITY_ID = "activity_id";

            /**
             * 圈子id
             */
            public static final String CLUSTER_ID = "cluster_id";

            /**
             * 审批状态 0.未处理 1.待审核 2.已通过 3.已删除
             */
            public static final String APPROVAL_BLANK = "approval_blank";

        }
    }

    /**
     * 用户索引
     */
    public static class UserIndex {

        /**
         * 索引名称
         */
        public static final String INDEX_NAME = "user_index";

        /**
         * 索引类型
         */
        public static final String INDEX_TYPE = "_doc";

        /**
         * 索引属性
         */
        public static class Properties {

            /**
             * 经纬度
             */
            public static final String USER_ID = "user_id";

            /**
             * 动态发送位置
             */
            public static final String NICK_NAME = "nick_name";

            /**
             * 动态置顶标志
             */
            public static final String SEX = "sex";

            /**
             * 动态展示类型
             */
            public static final String HEAD_PIC = "head_pic";

            /**
             * 用户类型
             */
            public static final String USER_TYPE = "user_type";

            /**
             * 最后登录的位置
             */
            public static final String LAST_POSITION = "last_position";

            /**
             * 最后的登陆时间
             */
            public static final String LOGIN_TIME = "login_time";

            /**
             * 数据恢复的时间
             */
            public static final String REVIVIFICATION_TIME = "revivification_time";

        }
    }
    /**
     * by lk KOL索引
     */
    public static class KolIndex {

        /**
         * 索引名称
         */
        public static final String INDEX_NAME = "kol_index";

        /**
         * 索引类型
         */
        public static final String INDEX_TYPE = "_doc";

        /**
         * 索引属性
         */
        public static class Properties {

            /**
             * KOl用户id
             */
            public static final String USER_ID = "user_id";

            /**
             * 一周内的点赞和评论数
             */
            public static final String COMMENT_AND_LIKE_COUNT = "comment_and_like_count";

            /**
             * KOl类型id
             */
            public static final String KOL_TYPE_ID = "kol_type_id";

            /**
             * KOl类型名称
             */
            public static final String KOL_TYPE_NAME = "kol_type_name";
            /**
             * KOl顶部图片
             */
            public static final String KOL_TITLE_IMAGE = "kol_title_image";
            /**
             * KOL用户头像
             */
            public static final String USER_PIC = "user_pic";
            /**
             * KOl用户性别
             */
            public static final String USER_SEX = "user_sex";
            /**
             * kol用户昵称
             */
            public static final String USER_NICK_NAME = "user_nick_name";
            /**
             * 创建时间
             */
            public static final String CREATE_TIME = "create_time";

        }
    }
}
