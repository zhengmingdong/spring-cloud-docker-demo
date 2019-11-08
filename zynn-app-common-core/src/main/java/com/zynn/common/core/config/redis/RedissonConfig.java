package com.zynn.common.core.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Redisson 配置
 * @Author zhanghao
 * @date 2019/3/26 10:24
 **/
@Configuration
public class RedissonConfig {


    @Autowired
    private RedisProperties redisProperties;


    
    /**
     * 创建 redisson 连接
     * @Author zhanghao
     * @Date  2019/3/26
     * @Param []
     * @return org.redisson.api.RedissonClient
     **/
    @Bean
    @Primary
    public RedissonClient redissonClient(){
        Config config = new Config();
        final ClusterServersConfig clusterServersConfig = config.useClusterServers();
        clusterServersConfig
                .setScanInterval(200);

        final List<String> nodes = redisProperties.getCluster().getNodes();

        String ip = null;

        for (String ipPort : nodes) {
            clusterServersConfig.addNodeAddress("redis://"+ipPort);
        }
        clusterServersConfig.setPassword(redisProperties.getPassword());
        clusterServersConfig.setSubscriptionConnectionPoolSize(10);
        clusterServersConfig.setSlaveConnectionMinimumIdleSize(5);
        clusterServersConfig.setSlaveConnectionPoolSize(10);
        clusterServersConfig.setMasterConnectionMinimumIdleSize(5);
        clusterServersConfig.setMasterConnectionPoolSize(10);
        clusterServersConfig.setRetryAttempts(1);
        clusterServersConfig.setTimeout(1000);
        clusterServersConfig.setConnectTimeout(2000);
        clusterServersConfig.setFailedSlaveCheckInterval(1);
        clusterServersConfig.setFailedSlaveReconnectionInterval(1000);


        config.setThreads(8);
        config.setNettyThreads(16);

        return Redisson.create(config);
    }

}
