package com.zynn.common.pojo.enums;

/**
 * @author 袁毅雄
 * @description 触发推送的模块
 * @date 2019/8/13
 */
public enum TriggerPushModuleTypeEnum {

    /**
     * 常规模块
     */
    ROUTINE,

    /**
     * 校园说模块
     */
    CAMPUS_TOPIC,

    /**
     * 故事模块
     */
    STORY,;

    @Override
    public String toString() {
        return this.name();
    }

}
