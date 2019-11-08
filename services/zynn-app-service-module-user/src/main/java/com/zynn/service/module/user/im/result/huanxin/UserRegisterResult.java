package com.zynn.service.module.user.im.result.huanxin;

import lombok.Data;

/**
 * 用户注册
 * @Author zhanghao
 * @date 2019/3/21 18:16
 **/
@Data
public class UserRegisterResult extends HuanxinBaseResult {


    //传入的username
    private String username;


    //传入的昵称
    private String nickname;


    //是否已激活
    private boolean activated;

}
