package com.zynn.api.yinian.gateway.constant;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yu_chen
 * @date 2018/5/17 13:52
 */
public class UrlConstant {


    /**
     * 放行登陆 接口
     * 需要被登录拦截器拦截
     */
    public static final List<String> PASS_SERVLET_PATHS = new ArrayList<String>() {{
        /**登录接口**/
        add("/user/api/v*/auth/accredit/**");
        add("/user/api/v*/auth/cellphone/**");
        //手机号密码登录
        add("/user/api/v*/auth/password/**");
        add("/user/api/v*/auth/setPassword/**");
        //游客登录
        add("/user/api/v*/auth/tourist/**");

        add("/user/v*/auth/**");

        add("/system/v1/appSysConfig/get/appDownload302");
    }};



    /**
     * 初始化不需要被zuul拦截过滤的接口
     */
    public static final List<String> PASS_NO_AUTHS = new ArrayList<String>() {{
        add("/es/api/**");

        /**登录前判断用户是否注册过**/
        add("/user/api/v*/auth/pre/password/**");
        /**短信验证码放行**/
        add("/system/api/v*/message/send/**");
        add("/activity/api/v*/oneWeekHotReview/getById/**");

        /**版本获取放行**/
        add("/system/api/v*/appSysVersionManage/get/version");
        /**app日志崩溃记录放行**/
        add("/system/api/v*/appSysBreakdownInfo/save");

        add("/*/v*/**");
    }};

    /**
     * 匹配查看路径是否匹配
     *
     * @param paths       路径
     * @param servletPath 请求的路径
     * @return true表示可以匹配上
     */
    public static boolean matchPath(List<String> paths, String servletPath) {
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String passServletPath : paths) {
            //判断路径是否匹配
            boolean match = pathMatcher.matchStart(passServletPath, servletPath);
            if (match) {
                //如果有一个匹配的 就跳出
                return true;
            }
        }
        return false;
    }

}
