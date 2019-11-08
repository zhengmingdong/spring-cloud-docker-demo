package com.zynn.common.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * 分页工具类
 */
public class PagingUtils {

    /**
     * 每页显示的数据条数
     */
    public static final String LIMIT = "limit";

    /**
     * 当前页码
     */
    public static final String PAGE = "page";

    /**
     * 获取页数
     *
     * @return
     */
    public static Integer getPage() {
        return getPage(1);
    }

    /**
     * 获取条数
     *
     * @return
     */
    public static Integer getLimit() {
        return getLimit(10);
    }


    /**
     * 获取页数，如果为空则使用默认值
     *
     * @param defaultValue
     * @return
     */
    public static Integer getPage(Integer defaultValue) {
        return Optional.ofNullable(getRequest().getAttribute(PagingUtils.PAGE)).map(Object::toString).map(Integer::valueOf).orElse(defaultValue);
    }

    /**
     * 获取分页条数，如果为空则使用默认值
     *
     * @param defaultValue
     * @return
     */
    public static Integer getLimit(Integer defaultValue) {
        return Optional.ofNullable(getRequest().getAttribute(PagingUtils.LIMIT)).map(Object::toString).map(Integer::valueOf).orElse(defaultValue);
    }


    /**
     * 获取开始数
     *
     * @return
     */
    public static Integer getStart() {
        return (getPage() - 1) * getLimit();
    }


    /**
     * 获取开始数,如果Page为空，则使用默认值
     *
     * @param defaultPageValue
     * @return
     */
    public static Integer getStart(Integer defaultPageValue) {
        return (getPage(defaultPageValue) - 1) * getLimit();
    }

    /**
     * 获取开始数,如果Page和limit为空，则使用默认值
     *
     * @param defaultPageValue
     * @return
     */
    public static Integer getStart(Integer defaultPageValue, Integer defaultLimitValue) {
        return (getPage(defaultPageValue) - 1) * getLimit(defaultLimitValue);
    }


    /**
     * request,手工设置参数
     * key->参数名称,value->参数的值
     *
     * @param param
     */
    public static void putParam(Map<String, Object> param) {
        param.keySet().forEach(key -> {
            getRequest().setAttribute(key, param.get(key));
        });
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }


}
