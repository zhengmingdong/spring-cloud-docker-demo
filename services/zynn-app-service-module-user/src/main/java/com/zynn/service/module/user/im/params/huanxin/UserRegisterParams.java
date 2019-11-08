package com.zynn.service.module.user.im.params.huanxin;

import com.zynn.service.module.user.im.params.IMBaseParams;
import lombok.Data;

/**
 * 用户注册参数
 * @Author zhanghao
 * @date 2019/3/21 18:03
 **/
@Data
public class UserRegisterParams extends HuanxinBaseParams {


    //用户名称
    private String username;

    //登录密码
    private String password;

    //昵称
    private String nikename;



}
