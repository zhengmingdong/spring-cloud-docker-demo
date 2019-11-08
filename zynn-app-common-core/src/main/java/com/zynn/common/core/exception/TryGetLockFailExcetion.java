package com.zynn.common.core.exception;

/**
 * 尝试获取锁失败异常
 * @Author zhanghao
 * @date 2019/4/16 18:46
 **/
public class TryGetLockFailExcetion extends Exception {

    public TryGetLockFailExcetion() {
    }

    public TryGetLockFailExcetion(String message) {
        super(message);
    }

    public TryGetLockFailExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public TryGetLockFailExcetion(Throwable cause) {
        super(cause);
    }

    public TryGetLockFailExcetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
