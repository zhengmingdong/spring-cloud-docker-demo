package com.zynn.service.module.user.streams;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

import static com.zynn.common.core.constant.QueueNameConstant.AUTHENTICATE_UPLOAD_PICTURE;

public interface AuthenticateUploadPictureSource {

    String OUTPUT = AUTHENTICATE_UPLOAD_PICTURE;


    @Output(OUTPUT)
    MessageChannel output();

}
