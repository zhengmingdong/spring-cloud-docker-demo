package com.zynn.monitor.admin.message;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    /**
     * 发送具体内容
     */
    private static final String MSG_TEMPLATE = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"%s;\" }}";

    /**
     * 发送钉钉消息
     * @param msg
     */
    public static void sendDingtalkMsg(String msg,String accessToken){
        msg = msg.replace("\"","");
        msg = String.format(MSG_TEMPLATE,msg);
        webClient.post().uri("https://oapi.dingtalk.com/robot/send?access_token="+accessToken)
                .header("Content-Type", "application/json; charset=utf-8")
                .body(Mono.just(msg), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(WebClientResponseException.class, err -> {
                    log.error("sendDingTalkMsg error ,  status:{},msg:{}", err.getRawStatusCode(), err.getResponseBodyAsString());
                }).subscribe(e -> log.error("result = {}",e));
    }


}
