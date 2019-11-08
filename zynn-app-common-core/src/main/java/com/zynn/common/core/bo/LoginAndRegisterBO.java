package com.zynn.common.core.bo;

import lombok.Data;

/**
 * 注册登录业务类
 * @author 刘凯
 * @since 2018-07-01
 */

@Data
public class LoginAndRegisterBO {
    /**
     * 微信登录时获取的 code
     */
    private String code;
    /**
     * 用户信息密文
     */
    private String encodeData;
    /**
     * 偏移向量
     */
    private String iv;
    /**
     * 用户来源客户端，PC、H5、小程序（各个小程序）、iOS、Android各渠道
     */
    private String source;
    /**
     * 来源客户端对应版本
     */
    private String version;
    /**
     * 用户端口，用于记录用户是通过哪种方式进入忆年，二维码、分享等
     */
    private String port;
    /**
     * 来源用户ID
     */
    private Integer fromUserID;
    /**
     * 来源空间ID
     */
    private Integer fromSpaceID;
    /**
     * 来源动态ID
     */
    private Integer fromEventID;
    private String enterFrom;

    /**
     * 会话密钥
     */
    private String sessionKey;
    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionID;

    /**
     * 昵称
     */
    private String wechatNickname;
    /**
     * 头像
     */
    private String wechatHeadPic;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 性别
     */
    private Integer wechatSex;

    /**
     * 用户标识
     */
    private Long userid;
}
