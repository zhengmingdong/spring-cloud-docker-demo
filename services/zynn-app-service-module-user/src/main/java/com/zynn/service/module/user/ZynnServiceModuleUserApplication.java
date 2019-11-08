package com.zynn.service.module.user;

import com.zynn.common.core.application.BaseApplication;
import org.springframework.boot.SpringApplication;

/**
 * EnableEurekaClient 对于注册中心而言，这个属于客户端
 *
 * @author yu_chen
 */
public class ZynnServiceModuleUserApplication extends BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZynnServiceModuleUserApplication.class, args);
    }

}
