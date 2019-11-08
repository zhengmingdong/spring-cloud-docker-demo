package com.zynn.common.core.bo;

import lombok.Data;

/**
 * @author wangyulin
 * @date 2019年7月17日18:27:56
 */
@Data
public class AuthorizationFacePicBO {

    private Integer code;

    private String message;

    private AuthorizationFacePicResultBO result;

}
