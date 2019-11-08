package com.zynn.service.module.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 环信配置信息类
 * @Author zhanghao
 * @date 2019/3/21 16:40
 **/
@Configuration
@ConfigurationProperties(prefix = "im.huanxin")
@Validated
@Data
public class HuanxinConfiguration {

    @NotEmpty
    @NotNull
    private String orgName;

    @NotEmpty
    @NotNull
    private String appName;


    @NotEmpty
    @NotNull
    private String clentId;

    @NotEmpty
    @NotNull
    private String clientSecret;



}
