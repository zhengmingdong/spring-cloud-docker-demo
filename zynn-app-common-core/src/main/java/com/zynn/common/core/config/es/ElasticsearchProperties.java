package com.zynn.common.core.config.es;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticsearchProperties {

    private String username;
    private String password;
    private String nodes;
    private String prefix;

}
