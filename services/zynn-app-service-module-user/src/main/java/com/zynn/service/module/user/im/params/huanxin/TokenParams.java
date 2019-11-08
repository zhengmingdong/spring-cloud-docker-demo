package com.zynn.service.module.user.im.params.huanxin;

import com.zynn.service.module.user.im.params.IMBaseParams;
import lombok.Data;

/**
 * 获取API权限请求参数
 * @Author zhanghao
 * @date 2019/3/21 18:04
 **/
@Data
public class TokenParams extends HuanxinBaseParams {


    //类型
    private String grant_type = "client_credentials";


    //环信 client id
    private String client_id;


    //环信 client secret
    private String client_secret;



}
