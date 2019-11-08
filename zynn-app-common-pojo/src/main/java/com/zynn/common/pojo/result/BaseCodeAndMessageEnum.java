package com.zynn.common.pojo.result;

import lombok.Getter;

/**
 * @author ChenYu
 * @date 2017-12-1 18:09:30
 */
@Getter
public enum BaseCodeAndMessageEnum implements CodeAndMessage{

    /**
     * 成功
     */
    SUCCESS(0, "接口调用成功！"),

    /**
     * 接口调用失败
     */
    FAIL(-1, "接口调用失败"),


    /**
     * 请求参数异常
     */
    BAD_REQUEST(400, "请求参数异常"),

    /**
     * 未认证成功
     */
    UN_AUTH(401, "token认证失败"),
    /**
     * 未认证成功
     */
    AUTH_OVERDUE(409, "token认证过期"),
    /**
     * 服务请求异常
     */
    SERVICE_ERROR(500, "服务请求异常"),

    /**
     * 服务断言异常
     */
    ASSERT_FAILURE_ERROR(501,"服务断言失败"),


    /**
     * 空指针异常
     */
    NULL_ERROR(502,"空指针异常"),

    ;


    private Integer code;

    private String message;

    BaseCodeAndMessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    @Override
    public String getMessage() {
        return message;
    }

}
