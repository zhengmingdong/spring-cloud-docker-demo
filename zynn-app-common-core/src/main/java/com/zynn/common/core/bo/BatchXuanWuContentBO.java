package com.zynn.common.core.bo;

import lombok.Data;

/**
 * @author wangyulin
 * @date 2019年3月24日15:33:40
 */
@Data
public class BatchXuanWuContentBO {

    private String to;

    private String content;

    public BatchXuanWuContentBO() {
    }

    public BatchXuanWuContentBO(String to, String content) {
        this.to = to;
        this.content = content;
    }
}
