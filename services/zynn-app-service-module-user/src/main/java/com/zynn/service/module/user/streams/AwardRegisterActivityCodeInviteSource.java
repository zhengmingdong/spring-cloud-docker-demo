package com.zynn.service.module.user.streams;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

import static com.zynn.common.core.constant.QueueNameConstant.AWARD_REGISTER_ACTIVITY_CODE_INVITE;

public interface AwardRegisterActivityCodeInviteSource {

    String OUTPUT = AWARD_REGISTER_ACTIVITY_CODE_INVITE;


    @Output(OUTPUT)
    MessageChannel output();
}
