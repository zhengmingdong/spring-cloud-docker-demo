package com.zynn.common.core.base;

import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

import static com.zynn.common.core.interceptor.impl.LogbackParameterInterceptor.USER_ID_KEY;

/**
 * 基础的Controller
 *
 * @author 涂炜
 * @date 2018年5月30日15:9:54
 */
@Slf4j
public class BaseController {

    @Autowired
    private ValueOperations valueOperations;


    protected HttpServletRequest request() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes == null ? null : servletRequestAttributes.getRequest();
    }

    protected HttpServletRequest getRequest() {
        return this.request();
    }

    /**
     * 打印请求的信息
     */
    protected void printRequestInfo() {
        StringBuilder paramBuilder = new StringBuilder();
        String requestInfo = "requestUrl:s%\nrequestParam:s%\nuserInfoKey:s%";
        //获取所有的请求参数
        Enumeration<String> paraNames = request().getParameterNames();
        while (paraNames.hasMoreElements()) {
            String thisName = paraNames.nextElement();
            String thisValue = request().getParameter(thisName);
            paramBuilder.append(thisName).append("=").append(thisValue).append("&");
        }
        String param = StringUtils.substringBeforeLast(paramBuilder.toString(), "&");
        //获取header中的用户信息
        String userInfoKey = request().getHeader("app-user-info-key");
        log.error(String.format(requestInfo, request().getRequestURI(), param, userInfoKey));


    }

    /**
     * 获取当前登录的用户ID
     *
     * @return Long UserId
     */
    protected Long getCurrentUserId() {
        Long userId = (Long) request().getAttribute(USER_ID_KEY);
        if (Objects.isNull(userId)) {
            String usrIdStr = request().getParameter(USER_ID_KEY);
            userId = Longs.tryParse(usrIdStr);
        }
        return userId;
    }


}
