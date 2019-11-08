package com.zynn.service.module.user.im.params.huanxin;

import lombok.Data;

/**
 * @author zhanghao
 * @date 2019/5/7 16:26
 **/
@Data
public class SendManagerMessageParams  extends HuanxinBaseParams{


    // 消息内容
    private String message;


    //接受的用户ID
    private Long toUserId;


    public SendManagerMessageParams() {
    }

    public SendManagerMessageParams(String message, Long toUserId) {
        this.message = message;
        this.toUserId = toUserId;
    }
}
