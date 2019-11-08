package com.zynn.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.zynn.common.core.bo.LoginAndRegisterBO;
import com.zynn.common.core.constant.WechatConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 微信数据解析接口
 * @author 刘凯
 * @date 2018-07-01
 */
public class WechatUtil {
    /**
     * 获取校验接口url
     * @param source 来源
     * @param code 微信数据
     * @return 请求链接
     */
    public String setWechatJscode2sessionUrl(String source, String code) {
        String wechatJscode2sessionUrl = "";
        switch (source) {
            case "小程序":
                wechatJscode2sessionUrl = WechatConstant.GET_WECHAT_SESSION_KEY_URL;
                break;
            case "玩图小程序":
                wechatJscode2sessionUrl = WechatConstant.PLAY_IMAGE_GET_WECHAT_SESSION_KEY_URL;
                break;
            case "测试小程序":
                wechatJscode2sessionUrl = WechatConstant.TEST_GET_WECHAT_SESSION_KEY_URL;
                break;
            case "精简版小程序":
                wechatJscode2sessionUrl = WechatConstant.JJ_GET_WECHAT_SESSION_KEY_URL;
                break;
            default:
                wechatJscode2sessionUrl = WechatConstant.GET_WECHAT_SESSION_KEY_URL;
                break;
        }
        wechatJscode2sessionUrl += code;
        return wechatJscode2sessionUrl;
    }
    /**
     * 微信登录凭证校验`
     * @param restTemplate service中传递过来
     * @param loginAndRegisterBO 用户信息
     * @return 接口调用是否成功
     */
    public Boolean getWechatResult(RestTemplate restTemplate,LoginAndRegisterBO loginAndRegisterBO) {
        String wechatJscode2sessionUrl = setWechatJscode2sessionUrl(loginAndRegisterBO.getSource(), loginAndRegisterBO.getCode());

        String resultJosn = restTemplate.postForObject(wechatJscode2sessionUrl,null, String.class);
        Map<String, Object> resultMap = JsonUtil.json2map(resultJosn);
        if(!Objects.isNull(resultMap.get(WechatConstant.ERRCODE))) {
            return false;
        }
        String sessionKey = Objects.toString(resultMap.get(WechatConstant.SESSION_KEY),"");
        String openid = Objects.toString(resultMap.get(WechatConstant.OPENID),"");
        String unionID = Objects.toString(resultMap.get(WechatConstant.UNIONID),"");
        loginAndRegisterBO.setSessionKey(sessionKey);
        loginAndRegisterBO.setOpenid(openid);
        loginAndRegisterBO.setUnionID(unionID);
        return true;
    }

}
