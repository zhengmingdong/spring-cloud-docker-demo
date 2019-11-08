package com.zynn.common.core.receiver;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 消息消费者
 */
@Component
@Slf4j
public class KafkaReceiver {

    /**
     * 直接消费
     *
     * @param record
     */
    @KafkaListener(topics = {"test-send"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("------------------------------kafkalisten---------------------------------------");
            System.out.println("record =" + record);
            System.out.println("message =" + message);
        }
    }


    /**
     * 延迟消费
     *
     * @param record
     */
    @KafkaListener(id = "test-delay", topics = {"test-delay"}, containerFactory = "batchFactory")
    public void delayListen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("------------------------------kafkalisten---------------------------------------");
            System.out.println("record =" + record);
            System.out.println("message =" + message);
        }
    }


    @Bean("batchFactory")
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        //关闭批量消费功能
        factory.setBatchListener(false);
        //不自动启动
        factory.setAutoStartup(false);
        configurer.configure(factory, consumerFactory);
        return factory;
    }
}