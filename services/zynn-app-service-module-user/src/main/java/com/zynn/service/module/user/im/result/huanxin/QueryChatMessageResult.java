package com.zynn.service.module.user.im.result.huanxin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 查询聊天消息结果
 * @Author zhanghao
 * @date 2019/3/21 18:36
 **/
@Data
public class QueryChatMessageResult extends HuanxinBaseResult {

    //其中一个用户名称
    private String userName;

    //另外一个用户名称
    private String anotherUserName;

    //消息列表
    private List<Message> messageList;

    @Data
    public static class Message {

        //消息发送者
        private String from;

        //消息接收者
        private String to;

        //消息id
        private String msgId;

        //消息时间
        private Timestamp timestamp;

        //消息JSON
        private JSONObject message;


    }

}

