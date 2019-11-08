package com.zynn.common.core.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义 health 端点自定义的类
 */
@Configuration
public class HealthIndicatorConfig {


    @Bean("elasticsearchRestHealthIndicator")
    public HealthIndicator elasticsearchRestHealthIndicator() {
        return new MyElasticsearchRestHealthIndicator();
    }


    private class MyElasticsearchRestHealthIndicator implements HealthIndicator {

        @Override
        public Health health() {
            return Health.up().build();
        }
    }

}
