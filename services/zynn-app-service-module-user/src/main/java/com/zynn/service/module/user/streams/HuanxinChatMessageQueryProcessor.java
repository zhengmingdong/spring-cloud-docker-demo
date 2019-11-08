package com.zynn.service.module.user.streams;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import static com.zynn.common.core.constant.QueueNameConstant.*;

public interface HuanxinChatMessageQueryProcessor {

    String INPUT = HUANXIN_CHAT_MESSAGE_RECORD + INPUT_SUFFIX_LOWER;

    String OUTPUT = HUANXIN_CHAT_MESSAGE_RECORD + OUTPUT_SUFFIX_LOWER;


    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
