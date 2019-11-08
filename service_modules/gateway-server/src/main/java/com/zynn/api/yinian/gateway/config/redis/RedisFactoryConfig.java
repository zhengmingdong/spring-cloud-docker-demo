package com.zynn.api.yinian.gateway.config.redis;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.internal.HostAndPort;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DnsResolvers;
import io.lettuce.core.resource.MappingSocketAddressResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@Slf4j
public class RedisFactoryConfig {


    @Autowired
    private RedisProperties redisProperties;



    @Value("${spring.profiles.active}")
    private String active;


    @Bean
    @Primary
    public RedisConnectionFactory myLettuceConnectionFactory(GenericObjectPoolConfig poolConfig) {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        final List<String> nodeList = redisProperties.getCluster().getNodes();
        Set<RedisNode> nodes = new HashSet<RedisNode>();

        String ip = null;

        for (String ipPort : nodeList) {
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
            ip = ipAndPort[0].trim();
        }
        redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());

//        final String ipStr = ip;
//
//
//        MappingSocketAddressResolver resolver = MappingSocketAddressResolver.create(DnsResolvers.UNRESOLVED ,
//                hostAndPort -> {
//                    if(hostAndPort.getHostText().startsWith("172")){
//                        return HostAndPort.of(ipStr, hostAndPort.getPort());
//                    }
//                    return hostAndPort;
//                });

        ClientResources clientResources = ClientResources.builder()
//                .socketAddressResolver(resolver)
                .build();


        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(redisProperties.getTimeout())
                .clientResources(clientResources)
                .poolConfig(poolConfig)
                .build();

        RedisClusterClient clusterClient ;

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
