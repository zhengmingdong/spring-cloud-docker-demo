package com.zynn.common.core.exception;

import com.zynn.common.pojo.result.CodeAndMessage;

/**
 * 业余异常
 */
public class BusinessExcetion extends RuntimeException {

    private CodeAndMessage codeAndMessage;

    public BusinessExcetion(CodeAndMessage codeAndMessage) {
        this.codeAndMessage = codeAndMessage;
    }

    public CodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }
}
