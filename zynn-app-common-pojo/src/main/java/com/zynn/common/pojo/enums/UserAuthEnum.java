package com.zynn.common.pojo.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * 用户渠道枚举
 *
 * @author wangyulin
 * @date 2019年2月21日14:03:44
 */
public enum UserAuthEnum {

    /**
     * 手机号渠道注册
     */
    CELLPHONE("cellphone", "手机号渠道注册"),
    /**
     * 微信渠道注册
     */
    WEI_XIN("WeiXin", "微信渠道注册"),
    /**
     * QQ渠道注册
     */
    QQ("QQ", "QQ渠道注册");


    private String key;
    private String description;

    UserAuthEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public static UserAuthEnum get(Integer key) {
        return Arrays.stream(UserAuthEnum.values()).filter(e -> Objects.equals(key, e.getKey())).count() > 0 ?
                Arrays.stream(UserAuthEnum.values()).filter(e -> Objects.equals(key, e.getKey())).findAny().get() : null;
    }
}