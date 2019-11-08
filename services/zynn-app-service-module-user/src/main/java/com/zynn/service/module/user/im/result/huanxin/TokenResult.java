package com.zynn.service.module.user.im.result.huanxin;

import com.zynn.service.module.user.im.result.IMBaseResult;
import lombok.Data;

/**
 * 获取API权限请求结果
 * @Author zhanghao
 * @date 2019/3/21 18:14
 **/
@Data
public class TokenResult extends HuanxinBaseResult {

    //有效的 token 字符串
    private String access_token;


    //有效时间
    private Long expires_in;





}
