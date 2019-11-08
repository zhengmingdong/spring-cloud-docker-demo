package com.zynn.common.core.config.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zynn.common.core.constant.ProfilesConstant;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * @author ChenYu
 * @date 2018年4月19日17:31:59
 */
@Configuration
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MybatisPlusConfig {

    private final MybatisPlusProperties properties;

    public MybatisPlusConfig(MybatisPlusProperties properties) {
        this.properties = properties;
    }

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    @ConditionalOnExpression("#{!'prod'.equals(environment.getProperty('spring.profiles.active'))}")
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 开启 PageHelper 的支持
        return paginationInterceptor;
    }


    @Value("${spring.profiles.active:dev}")
    private String active;

    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(properties.resolveMapperLocations());
        factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        MybatisConfiguration configuration = this.properties.getConfiguration();
        if(!ProfilesConstant.PRO_ACTIVE.equals(active)){
            factory.setPlugins(new Interceptor[]{paginationInterceptor(), performanceInterceptor()});
        }else{
            factory.setPlugins(new Interceptor[]{paginationInterceptor()});
        }
        GlobalConfig globalConfig = properties.getGlobalConfig();;
        factory.setConfiguration(configuration);
        factory.setGlobalConfig(globalConfig);
        return factory;
    }
}
