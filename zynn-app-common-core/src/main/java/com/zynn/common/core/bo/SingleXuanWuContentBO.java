package com.zynn.common.core.bo;

import lombok.Data;

/**
 * @author wangyulin
 * @date 2019年3月24日15:33:40
 */
@Data
public class SingleXuanWuContentBO {

    private String to;

    private String customMsgID;

    public SingleXuanWuContentBO() {
    }

    public SingleXuanWuContentBO(String to, String customMsgID) {
        this.to = to;
        this.customMsgID = customMsgID;
    }
}
