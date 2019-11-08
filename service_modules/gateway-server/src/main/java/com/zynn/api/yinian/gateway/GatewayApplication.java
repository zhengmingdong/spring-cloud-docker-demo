package com.zynn.api.yinian.gateway;

import com.zynn.api.yinian.gateway.handler.GlobalExcetionHandler;
import com.zynn.api.yinian.gateway.utils.SnowFlake;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

@SpringCloudApplication
@EnableBinding({Sink.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }



    /**
     * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
     * @param viewResolversProvider
     * @param serverCodecConfigurer
     * @return
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                             ServerCodecConfigurer serverCodecConfigurer) {
        GlobalExcetionHandler globalExcetionHandler = new GlobalExcetionHandler();
        globalExcetionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        globalExcetionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        globalExcetionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return globalExcetionHandler;
    }


    @Autowired
    private ValueOperations valueOperations;

    @Bean
    public SnowFlake createSnowFlake(@Value("${spring.application.name}") String applicationName){
        final String key = "SERVICE_MACHINE_ID".concat("_").concat(applicationName);
        final Long machineId = valueOperations.increment(key);
        if (machineId >  120) {
            valueOperations.getOperations().delete(key);
        }
        return new SnowFlake(0, machineId);
    }


}
