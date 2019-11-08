package com.zynn.common.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 袁毅雄
 * @description 标签
 * @date 2019/7/2
 */
@Data
@Accessors(chain = true)
public class LabelVO {

    /**
     * 标签名称
     */
    private String labelName;

    private LabelType labelType;

    public enum LabelType {

        EXPERT("expert", 1, "达人标签"),

        PERSONAGE("personage", 2, "个人标签"),

        CONTENT("content", 3, "内容标签");

        private String key;

        private Integer value;

        private String describe;

        LabelType(String key, Integer value, String describe) {
            this.key = key;
            this.value = value;
            this.describe = describe;
        }

        @Override
        public String toString() {
            return this.key;
        }
    }
}