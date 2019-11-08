package com.zynn.service.module.user.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 初始化 spring bean
 * @author: 刘天元
 * @create: 2018-06-01 14:14
 **/
@Configuration
public class InitUserModuleBean {

    /**
     * 记录feign的log
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
