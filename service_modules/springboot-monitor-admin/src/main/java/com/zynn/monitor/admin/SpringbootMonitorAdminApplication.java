package com.zynn.monitor.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 用于服务断路器追踪
 *
 * @author 刘天元
 * @date 2018年7月3日20:34:51
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@EnableDiscoveryClient
@ComponentScan(basePackages= {"com.zynn.monitor.admin"})
@EnableScheduling
public class SpringbootMonitorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMonitorAdminApplication.class, args);
    }


    @Bean
    @Primary
    public WebClient webClient(){
        return WebClient.builder()
                .build();
    }
}
