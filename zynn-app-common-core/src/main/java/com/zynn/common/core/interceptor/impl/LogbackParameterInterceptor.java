package com.zynn.common.core.interceptor.impl;

import com.alibaba.fastjson.JSONObject;
import com.zynn.common.core.dto.LoginAndRegisterResultDTO;
import com.zynn.common.core.interceptor.AbstractInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author zhanghao
 * @description log参数拦截器
 * @date 2019/6/18
 **/
@Component
@Slf4j
public class LogbackParameterInterceptor extends AbstractInterceptor {


    @Autowired
    private ValueOperations valueOperations;

    public static final String USER_ID_KEY = "userId";

    /**
     * 在请求处理之前回调方法
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final Long userId = getCurrentUserId(request);
        MDC.put(USER_ID_KEY, String.valueOf(userId));
        request.setAttribute(USER_ID_KEY, userId);
        return true;
    }


    /**
     * 请求处理之后回调方法
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        /* 线程结束后需要清除,否则当前线程会一直占用这个requestId值 */
        MDC.remove(USER_ID_KEY);
    }

    /**
     * 整个请求处理完毕回调方法
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        /*整个请求线程结束后需要清除 */
        MDC.clear();
    }

    @Override
    public String interceptorPath() {
        return "/**";
    }


    protected Long getCurrentUserId(HttpServletRequest request) {
        String userId = request.getHeader("loginUserId");
        if (StringUtils.isBlank(userId)) {
            //跳过Zuul接口,未放置loginUserId,重新解析app-user-info-key获取userId
            String md5Str = request.getHeader("app-user-info-key");
            if (StringUtils.isBlank(md5Str)) {
                return null;
            }
            String s = Optional.ofNullable(valueOperations.get(md5Str)).orElse("").toString();
            if (StringUtils.isBlank(s)) {
                return null;
            }
            LoginAndRegisterResultDTO user = Optional.ofNullable(JSONObject.parseObject(s, LoginAndRegisterResultDTO.class))
                    .orElse(new LoginAndRegisterResultDTO());
            return Long.valueOf(user.getId());
        }
        return Long.parseLong(userId);

    }
}