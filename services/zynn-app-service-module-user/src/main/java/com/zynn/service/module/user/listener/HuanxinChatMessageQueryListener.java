package com.zynn.service.module.user.listener;

import com.zynn.common.core.utils.KafkaUtils;
import com.zynn.service.module.user.streams.HuanxinChatMessageQueryProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 环信聊天消息查询监听器
 *
 * @Author zhanghao
 * @date 2019/3/26 18:57
 **/
@Component
@Slf4j
@EnableBinding(HuanxinChatMessageQueryProcessor.class)
public class HuanxinChatMessageQueryListener {


    @Value("${spring.profiles.active:dev}")
    private String active;


    @Autowired
    private KafkaUtils kafkaUtils;

    @Autowired
    private HuanxinChatMessageQueryProcessor huanxinChatMessageQueryProcessor;

    @StreamListener(HuanxinChatMessageQueryProcessor.INPUT)
    public void userHuanxinRegister(String json) {

    }
}
