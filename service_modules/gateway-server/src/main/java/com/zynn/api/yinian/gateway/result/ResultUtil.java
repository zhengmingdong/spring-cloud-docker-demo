package com.zynn.api.yinian.gateway.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果集工具类
 *
 * @author yu_chen
 * @date 2017-12-1 18:09:45
 **/
@SuppressWarnings("unchecked")
public class ResultUtil {


    /**
     * 获取返回值为Map的Result
     *
     * @param key   key
     * @param value value
     * @return Result 对象
     */
    public static Result<Map<String, Object>> resultMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>(16);
        map.put(key, value);
        return success(map);
    }

    /**
     * 成功或者失败
     *
     * @return result对象
     */
    public static Result successOrFail(boolean result) {
        //根据返回的状态得到result对象
        return result ? success() : fail();
    }

    /**
     * 成功
     *
     * @param object 传递的数据对象
     * @return result
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMessage());
        checkObjectIsBaseType(result, object);
        return result;
    }

    /**
     * 成功
     *
     * @param resultEnum 自定的状态值
     * @param object 传递的数据对象
     * @return result
     */
    public static Result success(ResultEnum resultEnum,Object object) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMessage());
        checkObjectIsBaseType(result, object);
        return result;
    }

    /**
     * 成功
     *
     * @param message 传递的数据对象
     * @param object  传递的数据对象
     * @return result
     */
    public static Result successMessage(String message, Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(message);
        checkObjectIsBaseType(result, object);
        return result;
    }

    /**
     * 成功
     *
     * @return result success
     */
    public static Result success() {
        return success("");
    }

    /**
     * 失败
     *
     * @param code 失败错误码
     * @param msg  失败的消息
     * @return result
     */
    public static Result fail(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        Map<String, Object> map = new HashMap<>();
        map.put("resultData", "");
        result.setData(map);
        return result;
    }

    /**
     * 失败
     * code =fail code
     * msg 需要自定义
     *
     * @param msg 错误吗
     * @return result
     */
    public static Result fail(String msg) {
        Result result = new Result();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMsg(msg);
        Map<String, Object> map = new HashMap<>();
        map.put("resultData", "");
        result.setData(map);
        return result;
    }

    /**
     * 提示接口调用失败的方法
     *
     * @param resultEnum 枚举对象
     * @return result
     */
    public static Result fail(ResultEnum resultEnum) {

        return fail(resultEnum.getCode(), resultEnum.getMessage());
    }

    /**
     * 不需要知道状态情况下调用此方法
     *
     * @return new Result(-1,"接口调用失败")
     */
    public static Result fail() {
        return fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage());
    }

    /**
     * 判断是否返回成功
     */
    public static boolean isReturnSuccess(Result result) {
        return null != result && 0 == result.getCode();
    }


    /**
     * 判断返回的结果是否是map类型
     *
     * @param result 结果集
     * @param object 需要返回的结果对象
     */
    private static void checkObjectIsBaseType(Result result, Object object) {
        //如果是字符串类型并且为空字符串就返回
        if (object instanceof String) {
            Map<String, Object> map = new HashMap<>();
            map.put("resultData", object);
            result.setData(map);
            return;
        }
        try {
            boolean type = ((Class<?>) object.getClass().getField("TYPE").get(null)).isPrimitive();
            if (type) {
                //如果是基本类型或者字符串类型
                Map<String, Object> map = new HashMap<>();
                map.put("resultData", object);
                result.setData(map);
                return;
            }
        } catch (Exception e) {
            result.setData(object);
            return;
        }
        result.setData(object);
    }
}
