package com.zynn.api.yinian.gateway.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口加密(验签配置项)
 * @Author lij
 */
public class SignConstant {

    public static final String CONSTANT_REQUEST = "requestId";
    public static final String CONSTANT_SIGN = "sign";

    public static final String METHOD_GET = "GET";
    public static final String REDIS_PRE = "app_url_verify";

    public static final String INNER_SIGN = "LIJ$%5(,!@#123a.)";
    /**
     * 需要接口验证的地址
     */
    public static final List<String> VERIFY_PATH = new ArrayList<String>(){{
        add("/**/api/v*/**");
    }};

    /**
     * 不需要接口验证的地址
     */
    public static final List<String> NO_VERIFY_PATH = new ArrayList<String>(){{
        //登录接口
        add("/user/api/v*/auth/accredit/**");
        //手机号登录
        add("/user/api/v*/auth/cellphone/**");
        //手机号密码登录
        add("/user/api/v*/auth/password/login/**");
        //短信验证码放行
        add("/system/api/v*/message/send/**");
        //游客登录]
        add("/user/api/v*/auth/tourist/login/**");

        add("/**/v*/**");

        add("/activity/**");
    }};

    /**
     * appId、secretId四则运算
     * @return
     */
    public static String encryptRule(String requestId){
       return 4022119+requestId;
    }


}
