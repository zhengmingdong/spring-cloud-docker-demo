package com.zynn.service.module.es;

import com.zynn.common.core.application.BaseApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;


public class ZynnServiceModuleEsApplication extends BaseApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(ZynnServiceModuleEsApplication.class, args);
    }
}
