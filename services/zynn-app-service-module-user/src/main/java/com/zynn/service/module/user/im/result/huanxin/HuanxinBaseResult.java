package com.zynn.service.module.user.im.result.huanxin;

import com.zynn.service.module.user.im.result.IMBaseResult;
import lombok.Data;

/**
 * @Author zhanghao
 * @date 2019/3/21 18:20
 **/
@Data
public class HuanxinBaseResult implements IMBaseResult {


    private Integer responseCode;

    private String error_description;

    private String  error;


}
