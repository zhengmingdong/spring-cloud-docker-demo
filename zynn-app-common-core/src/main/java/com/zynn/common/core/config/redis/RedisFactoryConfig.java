package com.zynn.common.core.config.redis;

import io.lettuce.core.internal.HostAndPort;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DnsResolvers;
import io.lettuce.core.resource.MappingSocketAddressResolver;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.*;

@Configuration
public class RedisFactoryConfig {


    @Autowired
    private RedisProperties redisProperties;

    /**
     * @Author zhanghao
     * @Description   创建lettuce连接工厂
     * @Date  2019/3/20
     * @Param [poolConfig]
     * @return org.springframework.data.redis.connection.RedisConnectionFactory
     **/
    @Bean
    @Primary
    public RedisConnectionFactory myLettuceConnectionFactory(GenericObjectPoolConfig poolConfig) {
//         集群版配置
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        final List<String> nodeList = redisProperties.getCluster().getNodes();
        Set<RedisNode> nodes = new HashSet<RedisNode>();

        String ip = null;

        for (String ipPort : nodeList) {
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
            ip = ipAndPort[0].trim();
        }

        final String ipStr = ip;


        MappingSocketAddressResolver resolver = MappingSocketAddressResolver.create(DnsResolvers.UNRESOLVED ,
                hostAndPort -> {
                    if(hostAndPort.getHostText().startsWith("172.31")){
                        return HostAndPort.of(ipStr, hostAndPort.getPort());
                    }
                    return hostAndPort;
                });

        ClientResources clientResources = ClientResources.builder()
                .socketAddressResolver(resolver)
                .build();

        redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(redisProperties.getTimeout())
                .clientResources(clientResources)
                .poolConfig(poolConfig)
                .build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisClusterConfiguration,clientConfig);
        return factory;

    }


    /**
     * GenericObjectPoolConfig 连接池配置
     *
     * @return
     */
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        RedisProperties.Pool pool = redisProperties.getLettuce().getPool();
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(pool.getMinIdle());
        genericObjectPoolConfig.setMinIdle(pool.getMaxIdle());
        genericObjectPoolConfig.setMaxTotal(pool.getMaxActive());
        genericObjectPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
        return genericObjectPoolConfig;
    }


}
