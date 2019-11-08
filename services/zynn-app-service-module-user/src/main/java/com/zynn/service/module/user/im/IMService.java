package com.zynn.service.module.user.im;

import com.zynn.service.module.user.im.params.IMBaseParams;
import com.zynn.service.module.user.im.result.IMBaseResult;
import reactor.core.publisher.Mono;

/**
 * IM服务定义接口
 * @Author zhanghao
 * @date 2019/3/21 17:16
 **/
public  interface IMService<P extends IMBaseParams,R extends IMBaseResult>  {


    /**
     * 用户注册方法
     * @Author zhanghao
     * @Date  2019/3/21
     * @Param [params] 注册参数
     * @return R 注册结果
     **/
     Mono<R> userRegister(P params);

     /**
      * 更新用户昵称
      * @Author zhanghao
      * @Date  2019/3/25
      * @Param [params]
      * @return reactor.core.publisher.Mono<java.lang.Void>
      **/
     Mono<Object> updateUserNickname(P params);


}
