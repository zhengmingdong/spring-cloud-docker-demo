package com.zynn.common.core.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截器抽象类
 * 所有拦截器都只需要继承这个类即可
 */
public abstract class AbstractInterceptor  extends HandlerInterceptorAdapter {

    /**
     * 拦截路径
     * @return
     */
    public abstract String interceptorPath();

}
