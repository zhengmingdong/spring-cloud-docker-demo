package com.zynn.service.module.user.im.params.huanxin;

import lombok.Data;

/**
 * 将用户移出IM群组Params
 * @author wangyulin
 * @date 2019年7月2日14:36:50
 **/
@Data
public class RemoveUserParams extends HuanxinBaseParams{


    // 群名称
    private String clusterId;

    //群组描述
    private String userHuanXinName;

}
