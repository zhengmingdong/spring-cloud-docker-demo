package com.zynn.common.core.utils;

import com.zynn.common.core.config.redis.RedisKey;
import com.zynn.common.core.constant.RedisKeyConstants;
import com.zynn.common.core.sequence.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @description 创建主键id
 * @author 刘天元
 * @create 2018-06-05 13:43
 **/
@Slf4j
@Component
public class SequenceUtil implements InitializingBean {
    /**
     * 数据节点标识
     */
    private static final int DEFAULT_DATA_CENTER_ID = 1;

    /**
     * 机器标识
     */
    private static final int DEFAULT_MACHINE_ID = 1;

    /**
     * redis中储存的最大机器标识
     */
    private static final int MAX_MACHINE_ID_IN_REDIS = 120;

    private static SnowFlake SNOW_FLAKE;

    @Autowired
    private ValueOperations valueOperations;

    @Value("${spring.application.name}")
    private String applicationName;

    public static Long getId() {
        return SNOW_FLAKE.nextId();
    }

    private SequenceUtil() {}

    @Override
    public void afterPropertiesSet() {
        //获取dataCenterId
        Map<String, Integer> serviceMap = new HashMap<>(32);
        for(ServiceModuleEnum serviceModuleEnum : ServiceModuleEnum.values()) {
            serviceMap.put(serviceModuleEnum.getServiceName(), serviceModuleEnum.getDataCenterId());
        }
        Integer currentDataCenterId = serviceMap.get(applicationName);
        int dataCenterId = Optional.ofNullable(currentDataCenterId).orElse(DEFAULT_DATA_CENTER_ID);
        //获取machineId
        int machineId = DEFAULT_MACHINE_ID;
        RedisKey redisKey = new RedisKey(RedisKeyConstants.SERVICE_MACHINE_ID, applicationName);
        String realKey = redisKey.getRealKey();
        try{
            machineId = valueOperations.increment(realKey).intValue();
            if(machineId > MAX_MACHINE_ID_IN_REDIS) {
                valueOperations.getOperations().delete(realKey);
            }
        } catch (Exception e) {
            log.info("redis获取machineId异常");
        }
        SNOW_FLAKE = new SnowFlake(dataCenterId, machineId);
        log.info("{}的dataCenterId:{},machineId:{}", applicationName, dataCenterId, machineId);
    }

    private enum ServiceModuleEnum {
        /**
         * 微服务与dataCenterId对应关系
         */
        zuul("server-api-zuul",0),
        user("service-module-user",1),
        system("service-module-system",2),
        es("service-module-es",3),
        event("service-module-event",4),
        calculate("service-module-calculate",5),
        activity("service-module-activity",6),
        address("service-module-address",7);

        private String serviceName;
        private Integer dataCenterId;

        ServiceModuleEnum(String serviceName, Integer dataCenterId) {
            this.serviceName = serviceName;
            this.dataCenterId = dataCenterId;
        }

        public Integer getDataCenterId() {
            return dataCenterId;
        }

        public String getServiceName() {
            return serviceName;
        }
    }

}
