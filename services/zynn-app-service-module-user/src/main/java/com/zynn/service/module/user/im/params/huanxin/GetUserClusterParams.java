package com.zynn.service.module.user.im.params.huanxin;

import lombok.Data;

/**
 * 获取群组的信息Params
 * @author wangyulin
 * @date 2019年7月2日14:36:50
 **/
@Data
public class GetUserClusterParams extends HuanxinBaseParams{

    /**
     * 群组Id
     */
    private String clusterId;

}
