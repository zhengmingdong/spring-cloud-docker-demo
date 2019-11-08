package com.zynn.common.core.constant;

/**
 * @author liqi
 *
 * Es 推荐数据索引
 *
 * 确定不会有子类
 */
public final class EsRmdIndexConstant {

    /**
     *  微信关系链索引类 (包含手机号)
     */
    public static class weChatRelationIndex {
        /**
         * 手机号微信关系链
         */
        public static final String INDEX = "we_chat_relation_index";

        /**
         * 手机号关系链 _doc
         */
        public static final String TYPE = "_doc";

        public static class Properties {
            /**
             * 邀请者电话
             */
            public static final String INVITER_TEL_PHONE="inviter_telphone";
            /**
             * 邀请者性别
             */
            public static final String INVITER_SEX="inviter_sex";
            /**
             * 邀请者省份
             */
            public static final String INVITER_PROVINCE="inviter_province";
            /**
             * 邀请者城市
             */
            public static final String INVITER_CITY="inviter_city";
            /**
             * 邀请者头像
             */
            public static final String INVITER_PIC="inviter_pic";
            /**
             * 邀请者昵称
             */
            public static final String INVITER_NICKNAME="inviter_nickname";
            /**
             * 邀请者微信id
             */
            public static final  String INVITER_WE_CHAT_ID="inviter_wechatid";
            /**
             * 被邀请者电话
             */
            public static final String BE_INVITER_TEL_PHONE="be_inviter_telphone";
            /**
             * 被邀请者性别
             */
            public static final String BE_INVITER_SEX="be_inviter_sex";
            /**
             * 被邀请者省份
             */
            public static final String BE_INVITER_PROVINCE="be_inviter_province";
            /**
             * 被邀请者城市
             */
            public static final String BE_INVITER_CITY="be_inviter_city";
            /**
             * 被邀请者头像
             */
            public static final String BE_INVITER_PIC="be_inviter_pic";
            /**
             * 被邀请者昵称
             */
            public static final String BE_INVITER_NICKNAME="be_inviter_nickname";
            /**
             * 被邀请者微信id
             */
            public static final String BE_INVITER_WE_CHAT_ID="be_inviter_wechatid";
        }
    }


    /**
     *
     * ES 存储可能认识的人
     */
    public static class weChatIndex{

        public static final String INDEX = "we_chat_index";

        public static final String TYPE = "_doc";

        public static class Properties{

            // 一度关系的id 集合
            public static final String ONCE_FRIEND_ID_SET = "onceFriendIdSet";

            // 二度关系的id 集合
            public static final String SECOND_FRIEND_ID_SET = "secondFriendIdSet";

            // 推荐可能认识的人
            public static final String RECOMMEND_FRIEND_V1 = "recommendFriendV1";
        }
    }


    /**
     * 微信用户信息
     */
    public static final class WechatUserInfoIndex{

        public static final String INDEX = "we_chat_user_info";

        public static final String TYPE = "_doc";

        public static class Properties{

            public static final String PROVINCE = "province";

            public static final String CITY = "city";

            public static final String SEX = "sex";

            public static final String TELPHONE = "telphone";

            public static final String NICKNAME = "nickname";

            public static final String WECHATID = "wechatid";

            public static final String PIC = "pic";

            public static final String REALNAME = "realname";

        }


    }
}
