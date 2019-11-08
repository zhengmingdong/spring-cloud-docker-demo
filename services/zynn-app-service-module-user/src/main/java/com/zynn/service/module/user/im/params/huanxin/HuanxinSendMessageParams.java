package com.zynn.service.module.user.im.params.huanxin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * @Author zhanghao
 * @date 2019/4/2 15:12
 **/
@Data
public class HuanxinSendMessageParams  {


    // 发送目标类型，给用户
    private String target_type = "users";


    //目标用户名称
    private List<String> target;


    //消息内容
    private TextMessage msg  = new TextMessage();


    //扩展信息
    private UserInfoExt ext;

    //发送者用户名称
    private String from;

    public void setMessage(String message){
        this.msg.setMsg(message);
    }

    @Data
    private static class TextMessage{

        //类型
        private String type = "txt";

        //消息内容
        private String msg;

    }




    @Setter
    public static class UserInfoExt{



        //用户ID
        private String YNChatUserId;

        //昵称
        private String YNChatUserNick;

        //头像地址
        private String YNChatUserPic;

        //用户ID
        private String YNUserId;

        //消息类型
        private String YNMsgType;

        //消息数据,json结构
        private String YNMsgData;

        @JsonProperty(value = "YNMsgType")
        public String getYNMsgType() {
            return YNMsgType;
        }

        @JsonProperty(value = "YNMsgData")
        public String getYNMsgData() {
            return YNMsgData;
        }

        @JsonProperty(value = "YNUserId")
        public String getYNUserId() {
            return YNUserId;
        }

        @JsonProperty(value = "YNChatUserId")
        public String getYNChatUserId() {
            return YNChatUserId;
        }

        @JsonProperty(value = "YNChatUserNick")
        public String getYNChatUserNick() {
            return YNChatUserNick;
        }

        @JsonProperty(value = "YNChatUserPic")
        public String getYNChatUserPic() {
            return YNChatUserPic;
        }
    }


    @Data
    public static class UserAttentionMessageInfo{

        //消息类型
        public static final String MESSAGE_TYPE = "attention";

        //单向关注
        public static final Integer ONE_WAY_ATTENTION = 1;

        //双向关注
        public static final Integer BOTHWAY_ATTENTION = 3;

        //关注关系: 0(无关系),1(a单向关注b),2(b单向关注a),3(互关)
        private Integer relation;

        //学校名称
        private String schoolName;

        //真实名称
        private String realName;

    }

}
