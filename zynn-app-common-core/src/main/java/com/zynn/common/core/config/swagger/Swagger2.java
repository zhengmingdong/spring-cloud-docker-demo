package com.zynn.common.core.config.swagger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.org.apache.regexp.internal.RE;
import com.zynn.common.core.application.BaseApplication;
import com.zynn.common.core.base.BaseController;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** 
* @Description: 生成接口文档
* @Author: 刘天元 
* @Date: 2018/6/1 10:24
*/ 
@Configuration
@EnableSwagger2
public class Swagger2  implements ApplicationContextAware {


    private String serviceName;


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zynn.service.module."+serviceName+".controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Timestamp.class, Date.class)
                .globalOperationParameters(getGlobalParameter());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(serviceName +" module APIs")
                .description(serviceName +" module APIs")
                .build();
    }



    private List<Parameter> getGlobalParameter(){
        List<Parameter> globalParameter = Lists.newArrayList(
                signParamet(),
                requestIdParamet(),
                tokenParamet(),
                pageParamet(),
                limitParamet()
        );
        return globalParameter;
    }

    private Parameter signParamet(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("sign")
                .defaultValue("LIJ$%5(,!@#123a.)")
                .description("校验参数签名(非登录类接口传递)")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(false);
        return parameterBuilder.build();
    }

    private Parameter requestIdParamet(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("requestId")
                .defaultValue("")
                .description("校验参数请求ID(非登录类接口传递)")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(false);
        return parameterBuilder.build();
    }


    private Parameter tokenParamet(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("app-user-info-key")
                .defaultValue("")
                .description("用户登录token key(非登录类接口传递)")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(false);
        return parameterBuilder.build();
    }



    private Parameter pageParamet(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("page")
                .defaultValue("1")
                .description("当前页数(分页类查询接口传递)")
                .modelRef(new ModelRef("int"))
                .parameterType("query")
                .required(false);
        return parameterBuilder.build();
    }

    private Parameter limitParamet(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("limit")
                .defaultValue("10")
                .description("一页条数(分页类查询接口传递)")
                .modelRef(new ModelRef("int"))
                .parameterType("query")
                .required(false);
        return parameterBuilder.build();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        final Map<String, BaseApplication> controllerList = applicationContext.getBeansOfType(BaseApplication.class);
        if (MapUtils.isNotEmpty(controllerList)){
            final BaseApplication baseApplication = controllerList.get(Lists.newArrayList(controllerList.keySet()).get(0));

            final String packageName = baseApplication.getClass().getPackage().getName();

            this.serviceName = getServicePackageName(packageName);

        }else{ //如果没有实现有，说明这个项目部署的不对，则设置一个找不到的值
            serviceName = "void";
        }
    }


    private String getServicePackageName(String packageName){
        final String lastPackage = packageName.substring(packageName.lastIndexOf(".") + 1);
        //如果获取的包名是application,说明这个类不是在最外层
        if (lastPackage.equals("application")){
            return getServicePackageName(packageName.substring(0,packageName.lastIndexOf(".")));
        }
        return lastPackage;
    }

}
