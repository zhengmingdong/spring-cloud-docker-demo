package com.zynn.service.module.user.im.params.huanxin;

import lombok.Data;

/**
 * 查询聊天消息参数
 * @Author zhanghao
 * @date 2019/3/21 18:34
 **/
@Data
public class QueryChatMessageParams extends HuanxinBaseParams {


    //聊天消息时间，格式为：YYYYMMDDHH
    private String time;



}
