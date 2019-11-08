package com.zynn.common.core.config.push;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "push")
@Data
public class AppPushProperties {

    public String appId;

    public String appKey;

    public String appSecret;

    public String masterSecret;

    public String clientId;
}
