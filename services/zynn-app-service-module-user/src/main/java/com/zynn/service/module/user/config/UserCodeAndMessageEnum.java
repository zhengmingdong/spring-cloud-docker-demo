package com.zynn.service.module.user.config;

import com.zynn.common.pojo.result.CodeAndMessage;


/**
 * @Author zhanghao
 * @Description 用户项目返回码和消息枚举
 * @Date  2019/3/20
 * @Param
 * @return
 **/
public enum  UserCodeAndMessageEnum implements CodeAndMessage {

    // 1001 - 1999 为用户模块错误码


    /**
     * 显示密码登录
     */
    SHOW_PASSWORD_LOG_IN(1001, "显示密码登录！"),

    /**
     * 该账号未填写资料
     */
    NOT_USER_INFO_ADD(1002, "该账号未填写资料！"),

    /**
     * 不显示密码登录
     */
    NOT_SHOW_PASSWORD_LOG_IN(1003, "不显示密码登录！"),


    /**
     * 该账号未注册
     */
    NOT_REGISTER(1004, "该账号未注册！"),

    /**
     * 该账号未注册
     */
    NICKNAME_ALREADY_USED(1005, "昵称已存在,换个其他的吧"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR(1006, "密码错误！"),

    /**
     * 未设置初始密码
     */
    UN_SET_PASSWORD(1007, "未设置初始密码！"),

    /**
     * imei 值不能为空
     */
    DEVICE_ERROR(1008, "imei.empty"),

    /**
     * device 值错误
     */
    DEVICE_INFO_ERROR(1009, "The value of the device must be Android or iOS"),

    ;

    private int code;

    private String message;

    UserCodeAndMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
