package com.zynn.common.core.utils;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 袁毅雄
 * @description Kafka工具
 * @date 2019/3/8
 */
@Log4j2
@Component("kafkaUtils")
public class KafkaUtils {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * [启停队列实现延迟投递]启动监听器,启动队列
     *
     * @param queueNames
     */
    public void kafkaStart(String... queueNames) {
        Arrays.stream(Optional.ofNullable((Object[]) queueNames).orElse(Lists.newArrayList().toArray()))
                .filter(Objects::nonNull)
                .map(x -> x.toString())
                .filter(StringUtils::isNotEmpty)
                .forEach(queueName -> {
                    log.info("启动监听器-启动队列:{}", queueName);
                    registry.getListenerContainer(queueName).start();
                    System.out.println(String.format("启动监听器-启动队列:{%s}", queueName));
                });
    }


    /**
     * [启停队列实现延迟投递]关闭监听器,停止队列
     *
     * @param queueNames 关闭的队列
     */
    public void kafkaStop(String... queueNames) {
        Arrays.stream(Optional.ofNullable((Object[]) queueNames).orElse(Lists.newArrayList().toArray()))
                .filter(Objects::nonNull)
                .map(x -> x.toString())
                .filter(StringUtils::isNotEmpty)
                .forEach(queueName -> {
                    log.info("关闭监听-停止队列{}", queueName);
                    registry.getListenerContainer(queueName).stop();
                    System.out.println(String.format("关闭监听-停止队列{%s}", queueName));
                });
    }


    /**
     * 发送消息
     *
     * @param msg        发送的消息
     * @param queueNames 发送到的队列
     */
    private void kafkaSend(Object msg, String... queueNames) {
        Arrays.stream(Optional.ofNullable((Object[]) queueNames).orElse(Lists.newArrayList().toArray()))
                .filter(Objects::nonNull)
                .map(x -> x.toString())
                .filter(StringUtils::isNotEmpty)
                .forEach(queueName -> {
                    log.info("发送kafka消息队列:{},数据:{}", queueName, msg);
                    kafkaTemplate.send(queueName, msg.toString());
                });
    }

    /**
     * 同步发送消息
     *
     * @param msg        发送的消息
     * @param queueNames 发送到的队列
     */
    public void synKafkaSend(Object msg, String... queueNames) {
        kafkaSend(msg, queueNames);
    }

    /**
     * 异步发送消息
     *
     * @param msg        发送的消息
     * @param queueNames 发送到的队列
     */
    public void unsynKafkaSend(Object msg, String... queueNames) {
        ThreadPoolManager.getInstance().execute(() -> kafkaSend(msg, queueNames));
    }

}
