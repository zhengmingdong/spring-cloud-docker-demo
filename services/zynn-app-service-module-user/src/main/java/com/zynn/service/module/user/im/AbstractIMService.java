package com.zynn.service.module.user.im;

import com.zynn.service.module.user.im.params.IMBaseParams;
import com.zynn.service.module.user.im.result.IMBaseResult;

/**
 * IM服务抽象实现类
 * @Author zhanghao
 * @date 2019/3/21 17:57
 **/
public abstract class AbstractIMService<P extends IMBaseParams,R extends IMBaseResult>  implements IMService<P,R> {


    public abstract  String getServiceName();


}
