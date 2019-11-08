package com.zynn.common.core.constant;

/**
 * 微信相关常量
 * @author 刘凯
 * @date 2018-07-01
 */
public class WechatConstant {
    /**
     * 忆年小程序appID
     */
    public final static String APP_ID = "wx48b3b26e45ad2e2e";

    /**
     * 忆年小程序secretID
     */
    public final static String SECRET_ID = "66e67cbc568429982766ce1780ff66e6";
    /**
     * 忆年小程序请求url
     */
    public final static String GET_WECHAT_SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret="
            + SECRET_ID + "&grant_type=authorization_code&js_code=";

    /**
     * 测试小程序appID
     */
    public final static String TEST_APP_ID = "wx14e7aa6429da2129";
    /**
     * 测试小程序secretID
     */
    public final static String TEST_SECRET_ID = "4cc91dd3c3366f3758f38b50f4c51f78";
    /**
     * 测试小程序请求url
     */
    public final static String TEST_GET_WECHAT_SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + TEST_APP_ID + "&secret="
            + TEST_SECRET_ID + "&grant_type=authorization_code&js_code=";

    /**
     * 精简版小程序appID
     */
    public final static String JJ_APP_ID = "wx73ef5bcc7a858a68";
    /**
     * 精简版小程序secretID
     */
    public final static String JJ_SECRET_ID = "735e912da4c0e673fde72d3907e87243";
    /**
     * 精简版小程序请求url
     */
    public final static String JJ_GET_WECHAT_SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + JJ_APP_ID + "&secret="
            + JJ_SECRET_ID + "&grant_type=authorization_code&js_code=";

    /**
     * 玩图小程序appID
     */
    public final static String PLAY_IMAGE_APP_ID = "wx228859fda649194f";
    /**
     * 玩图小程序secretID
     */
    public final static String PLAY_IMAGE_SECRET_ID = "db88623f1156d295cb86a61e3139f2cb";
    /**
     * 玩图小程序请求url
     */
    public final static String PLAY_IMAGE_GET_WECHAT_SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + PLAY_IMAGE_APP_ID
            + "&secret=" + PLAY_IMAGE_SECRET_ID + "&grant_type=authorization_code&js_code=";

    /**
     * 错误码
     */
    public final static String ERRCODE = "errcode";
    /**
     * 会话密钥
     */
    public final static String SESSION_KEY = "session_key";
    /**
     * 用户唯一标识
     */
    public final static String OPENID = "openid";

    /**
     * token
     */
    public final static String ACCESS_TOKEN = "access_token";

    /**
     * 用户在开放平台的唯一标识符
     */
    public final static String UNIONID = "unionid";

    /**
     * 前端验签
     */
    public final static String TICKET = "ticket";
}
