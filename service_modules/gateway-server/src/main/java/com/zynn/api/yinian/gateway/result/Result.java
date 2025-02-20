package com.zynn.api.yinian.gateway.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ChenYu
 * @date 2017-12-1 18:09:16
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据对象
     */
    private T data;

    public Result() {
    }

    public Result(T object) {
        this.data = object;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 如果result的data是Map类型 则可以通过此方法获取map对象
     * @return Map对象
     */
    @SuppressWarnings({"uncheck"})
    public Map<String,Object> dataMap(){
        if (this.data instanceof Map){
            return (Map<String, Object>) this.data;
        }
        return null;
    }
}
