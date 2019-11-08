package com.zynn.common.core.config.push.enums;

/**
 * @author 袁毅雄
 * @description 推送进度
 * @date 2019/8/13
 */
public enum PushCourseEnum {

    /**
     * kafaka-生成推送消息
     */
    KAFKA_PRODUCE,

    /**
     * kafaka-消费推送消息
     */
    KAFKA_RECEIVE,

    /**
     * 个推-推送开始
     */
    PUSH_START,

    /**
     * 个推-推送正常结束
     */
    PUSH_SUCCEED_OVER,

    /**
     * 个推-推送异常结束
     */
    PUSH_ERROR_OVER,

    /**
     * 短信-推送开始
     */
    SMS_START,

    /**
     * 短信-推送正常结束
     */
    SMS_SUCCEED_OVER,

    /**
     * 短信-推送异常结束
     */
    SMS_ERROR_OVER,

    /**
     * 用户服务调度异常结束
     */
    PUSH_REMOTE_SERVICE_USER_FAIL_OVER,

    /**
     * 未知服务调度异常结束
     */
    PUSH_REMOTE_SERVICE_UNKNOWN_FAIL_OVER;

    @Override
    public String toString() {
        return this.name();
    }

}
