package com.zynn.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 发送预警通知消息
 * @Author lijia
 * @Description
 * @Date 2018/10/19
 **/
@Slf4j
public class SendInformMessageUtil {

    private static HttpClient httpclient = HttpClients.createDefault();

    /**
     * 发送具体内容
     */
    private static final String MSG_TEMPLATE = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"%s;\" }}";


    /**
     * 发送告警通知
     * @param msgValue 消息内容
     * @Param accessToken 发送到钉钉的token
     * @return
     */
    public static boolean sendMsg(String msgValue,String accessToken) {
        msgValue = msgValue.replace("\"","");
        msgValue = String.format(MSG_TEMPLATE,msgValue);
        HttpPost httppost = new HttpPost("https://oapi.dingtalk.com/robot/send?access_token="+accessToken);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        httppost.setEntity(new StringEntity(msgValue, "utf-8"));
        log.info("=sendSMS=>msg=" + msgValue);

        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.info("<=sendSMS=>send ok ,msg=" + result);
            } else {
                log.error("<=sendSMS=>send error ,status={} ,msg=" + result, response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            log.error("<=sendSMS=>error:", e);
            return false;
        }
        return true;
    }


}
