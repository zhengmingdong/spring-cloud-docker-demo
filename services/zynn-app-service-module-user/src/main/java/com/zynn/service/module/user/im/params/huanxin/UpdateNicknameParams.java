package com.zynn.service.module.user.im.params.huanxin;

import lombok.Data;

/**
 * 更新昵称参数
 * @Author zhanghao
 * @date 2019/3/25 16:55
 **/
@Data
public class UpdateNicknameParams extends HuanxinBaseParams {


    //昵称
    private  String nickname;

    //用户名称
    private String username;


}
