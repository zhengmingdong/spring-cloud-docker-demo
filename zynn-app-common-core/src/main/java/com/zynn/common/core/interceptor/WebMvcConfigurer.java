package com.zynn.common.core.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述:拦截器配置
 *
 * @author 刘天元
 * @create 2018年10月8日14:09:45
 **/
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {


    @Resource
    private List<AbstractInterceptor> interceptorList;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptorList.forEach(e -> {
            registry.addInterceptor(e).addPathPatterns(e.interceptorPath());
        });

        super.addInterceptors(registry);
    }
}