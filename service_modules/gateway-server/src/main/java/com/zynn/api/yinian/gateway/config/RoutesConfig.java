package com.zynn.api.yinian.gateway.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


/**
 * 路由配置
 * 所有路由在此进行配置
 */
@Component
public class RoutesConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        final RouteLocatorBuilder.Builder routeBuilder = builder.routes();
         Lists.newArrayList(Service.values()).forEach(e -> {
             createRouteBulder(routeBuilder,e);
         });

        return routeBuilder.build();
    }


    /**
     * 用户路由配置
     * @param builder
     * @return
     */
    private RouteLocatorBuilder.Builder createRouteBulder(RouteLocatorBuilder.Builder builder,Service service){
        final String serviceName = service.name().toLowerCase();
        builder.route(service.getServiceName(), //路由ID
                r -> r.path(service.getServicePath()) // 拦截路径
                        .filters(f -> createFilters(serviceName,f)
                        )         //转发到的地址, lb:// 表示客户端负载均衡
                        .uri("lb://service-module-"+serviceName)
        );
        return builder;
    }






    @Value("${spring.profiles:active}")
    private String active;

    private GatewayFilterSpec createFilters(String servicePath,GatewayFilterSpec gatewayFilterSpec){
        gatewayFilterSpec.
                rewritePath("/"+servicePath+"/(?<segment>.*)","/${segment}")
                //修改返回体过滤器， 这里是为了适应 Body的长度，以免会被截断
                .modifyResponseBody(String.class,String.class,(exchange,s) -> Mono.just(s));

        if (!active.equals("dev")){
            // 开启断路器
            gatewayFilterSpec.hystrix(e -> e.setName("defaultCommand"));
        }
        return gatewayFilterSpec;
    }


    /**
     * 服务枚举
     */
    public static enum Service{

        USER("user_service","/user/**",true),
        ES("es_service","/es/**",false),
        ;

        //服务名称
        private String serviceName;

        //服务路径
        private String servicePath;

        //是否需要Swagger
        private Boolean needSwagger;

        Service(String serviceName, String servicePath, Boolean needSwagger) {
            this.serviceName = serviceName;
            this.servicePath = servicePath;
            this.needSwagger = needSwagger;
        }


        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServicePath() {
            return servicePath;
        }

        public void setServicePath(String servicePath) {
            this.servicePath = servicePath;
        }

        public Boolean getNeedSwagger() {
            return needSwagger;
        }

        public void setNeedSwagger(Boolean needSwagger) {
            this.needSwagger = needSwagger;
        }
    }




}
