package com.zynn.api.yinian.gateway.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LogInfo {

    /**
     * 请求唯一标识
     */
    private Long requestId;

    /**
     * 用户标识
     */
    private String userId;

    /**
     * jwt token
     */
    private String jwt;

    /**
     * 请求URI
     */
    private String requestUrl;

    /**
     * 操作方式
     */
    private String method;


    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 执行时间
     */
    private Long time;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 异常
     */
    private String exception;

    /**
     * 返回状态码
     */
    private Integer responseStatusCode;

    /**
     * 跳转ip
     */
    private String redirectHost;

    /**
     * 通知端口
     */
    private Integer redirectPort;

    /**
     * 返回值
     */
    private String responseData;

    /**
     * 版本
     */
    private String version;

    /**
     * 来源
     */
    private String source;

    /**
     * 手机app的序列号
     */
    private String imei;

    /**
     * 设备信息
     */
    private String deviceInfo;

}
