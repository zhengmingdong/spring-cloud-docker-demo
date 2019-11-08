package com.zynn.common.core.listener;

import com.zynn.common.core.utils.SendMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import static com.zynn.common.core.constant.KafkaErrorConstant.ERROR_CHANNEL;


/**
 * 错误消费接受队列
 * @Author zhanghao
 * @date 2019/4/1 17:43
 **/
//@Component
@Slf4j
public class ErrorChanneListener {



    @StreamListener(ERROR_CHANNEL)
    public void errorChannel(ErrorMessage errorMessage){
        try{
            GenericMessage genericMessage = (GenericMessage) errorMessage.getOriginalMessage();
            String paramsJson = null;
            if (genericMessage != null){
                byte[] bytes = (byte[]) genericMessage.getPayload();
                paramsJson  = new String(bytes);
            }else{
                MessagingException messagingException = (MessagingException) errorMessage.getPayload();
                GenericMessage message = (GenericMessage) messagingException.getFailedMessage();
                byte[] bytes = (byte[])  message.getPayload();
                paramsJson = new String(bytes);
            }
            final ConsumerRecord data = errorMessage.getHeaders().get("kafka_data", ConsumerRecord.class);
//            log.error(String.format(" listener error , topic = %s  ，params = %s",data.topic(),paramsJson),errorMessage.getPayload());
            SendMsgUtils.sendDingtalkMsg(String.format("[监听消费错误] topic : %s \r\n params : %s \r\n error : %s ",data.topic() ,paramsJson, errorMessage.getPayload().getMessage()));
        }catch (Exception ex){
            log.error("errorChannel error ",ex);
        }
    }


}
