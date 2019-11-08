package com.zynn.common.core.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringCloudApplication
@EnableFeignClients(basePackages = {"com.zynn.service.bridge.client.**"})
@ComponentScan(basePackages= {"com.zynn.**"})
@MapperScan("com.zynn.service.module.*.dao.**")
@EnableRetry
public class BaseApplication {
}
