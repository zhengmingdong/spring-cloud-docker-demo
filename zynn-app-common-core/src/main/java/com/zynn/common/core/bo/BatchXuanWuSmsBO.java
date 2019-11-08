package com.zynn.common.core.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyulin
 * @date 2019年3月24日15:33:40
 */
@Data
public class BatchXuanWuSmsBO {

    private String batchName = "batch";

    private List<BatchXuanWuContentBO> items = new ArrayList<>();

    private String msgType = "sms";

    private String bizType = "100";
}
