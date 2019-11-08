package com.zynn.service.module.user.im.exceptions;

/**
 * token 错误异常
 * @Author zhanghao
 * @date 2019/3/22 15:44
 **/
public class TokenFailExcetion extends RuntimeException {
    public TokenFailExcetion() {
    }

    public TokenFailExcetion(String message) {
        super(message);
    }

    public TokenFailExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenFailExcetion(Throwable cause) {
        super(cause);
    }

    public TokenFailExcetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
