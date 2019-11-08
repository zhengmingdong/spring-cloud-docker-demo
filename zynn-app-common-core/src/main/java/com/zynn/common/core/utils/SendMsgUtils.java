package com.zynn.common.core.utils;


import com.zynn.common.core.constant.ProfilesConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SendMsgUtils {


    private static WebClient webClient;

    @Autowired
    public  void setWebClient(WebClient webClient) {
        SendMsgUtils.webClient = webClient;
    }


    private static String  active;

    @Value("${spring.profiles.active:dev}")
    public  void setActive(String active) {
        SendMsgUtils.active = active;
    }



    /**
     * 发送具体内容
     */
    private static final String MSG_TEMPLATE = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"%s;\" }}";



    /**
     * 发送钉钉消息
     * @param msg
     */
    public static void sendDingtalkMsg(String msg){
        if (ProfilesConstant.PRO_ACTIVE.equalsIgnoreCase(active)){
            msg = msg.replace("\"","");
            msg = String.format(MSG_TEMPLATE,msg);
            webClient.post().uri("https://oapi.dingtalk.com/robot/send?access_token=358c9a56537639799be703754eb906821ac57bd655b80a224051dd82ab3b8a5a")
                    .header("Content-Type", "application/json; charset=utf-8")
                    .body(Mono.just(msg), String.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnError(WebClientResponseException.class, err -> {
                        log.error("sendDingTalk msg error ,  status:{},msg:{}", err.getRawStatusCode(), err.getResponseBodyAsString());
                    }).subscribe(e -> log.debug("result = {}",e));
        }
    }

    /**
     * 发送钉钉消息
     * @param content
     * @param token
     */
    public static void sendDingtalkMsgInCustom(String content,String token){
        if ("prod".equalsIgnoreCase(active)){
            content = content.replace("\"","");
            content = String.format(MSG_TEMPLATE,content);
            webClient.post().uri("https://oapi.dingtalk.com/robot/send?access_token="+token)
                    .header("Content-Type", "application/json; charset=utf-8")
                    .body(Mono.just(content), String.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnError(WebClientResponseException.class, err -> {
                        log.error("send dingtalk msg error ,  status:{},msg:{}", err.getRawStatusCode(), err.getResponseBodyAsString());
                    }).subscribe(e -> log.debug("result = {}",e));
        }
    }


}
