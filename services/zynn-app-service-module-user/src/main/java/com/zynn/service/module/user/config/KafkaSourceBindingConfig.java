package com.zynn.service.module.user.config;

import com.zynn.service.module.user.streams.*;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 *
 * @author zhanghao
 * @date 2019/5/30 14:45
 **/

@EnableBinding({
        AuthenticateUploadPictureSource.class,
        AwardRegisterActivityCodeInviteSource.class,
})
public class KafkaSourceBindingConfig {
}
