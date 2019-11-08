package com.zynn.common.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * web client 配置类
 * @Author zhanghao
 * @Date  2019/3/21
 * @Param
 * @return
 **/
@Configuration
@Slf4j
public class WebClientConfig {


    @Autowired
    private LoadBalancerExchangeFilterFunction lbFunction;

    @Bean("lbWebClient")
    public WebClient lbWebClient(){
        return WebClient.builder()
                .filter(lbFunction)
                .build();
    }

    @Bean
    @Primary
    public WebClient webClient(){
        return WebClient.builder()
                .build();
    }


}
