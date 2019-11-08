package com.zynn.service.module.user.im.params.huanxin;

import lombok.Data;

/**
 * @Author zhanghao
 * @date 2019/4/2 16:33
 **/
@Data
public class SendMessageParams  extends HuanxinBaseParams{

    //发送用户ID
    private Long fromUserId;


    //接收用户ID
    private Long toUserId;


    //消息内容
    private String message;

    //是否是是关注
    private Boolean attention;

    //关注关系，为关注的时候使用
    private Integer attentionRelation;

    public SendMessageParams() {
    }

    public SendMessageParams(Long fromUserId, Long toUserId, String message) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
    }

    public SendMessageParams(Long fromUserId, Long toUserId, String message,Boolean attention,Integer attentionRelation) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.attention = attention;
        this.attentionRelation = attentionRelation;
    }
}


