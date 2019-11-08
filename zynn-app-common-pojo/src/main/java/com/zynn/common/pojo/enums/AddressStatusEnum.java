package com.zynn.common.pojo.enums;

/**
 * @author liqi
 * 通讯录状态枚举
 */
public enum AddressStatusEnum {

    /**
     * 待邀请
     */
    to_be_invited(1, "待邀请"),

    /**
     * 已邀请
     */
    has_invited(2, "已邀请"),

    /**
     * 已关注
     */
    has_attention(3, "已关注"),

    /**
     * 未关注
     */
    to_be_attention(4, "未关注");

    private Integer key;

    private String describe;


    AddressStatusEnum(Integer key, String describe) {
        this.key = key;
        this.describe = describe;
    }


    public Integer getKey() {
        return key;
    }
}
