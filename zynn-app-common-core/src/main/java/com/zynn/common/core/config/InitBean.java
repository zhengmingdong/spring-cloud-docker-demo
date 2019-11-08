package com.zynn.common.core.config;

import com.zynn.common.core.handler.GlobalExceptionHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @description: 初始化 spring bean
 * @author: 刘天元
 * @create: 2018-06-01 14:14
 **/
@Configuration
public class InitBean {

    /**
     * @Description: 初始化BaseExceptionHandler
     * @Author: 刘天元
     * @Date: 2018/6/1 14:26
     */
    @Bean
    public GlobalExceptionHandler initBaseExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    /**
     * @Description: 初始化RestTemplate
     * @Author: 刘天元
     * @Date: 2018-6-21 14:36:37
     */
    @Bean
    public RestTemplate initRestTemplate() {
        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
        return restTemplate;
    }


}
