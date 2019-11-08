package com.zynn.api.yinian.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zynn.api.yinian.gateway.bean.LogInfo;
import com.zynn.api.yinian.gateway.constant.LoginUserConstant;
import com.zynn.api.yinian.gateway.excetions.SingleLoginException;
import com.zynn.api.yinian.gateway.result.Result;
import com.zynn.api.yinian.gateway.result.ResultEnum;
import com.zynn.api.yinian.gateway.service.LogService;
import com.zynn.api.yinian.gateway.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service("esLogService")
@Slf4j
public class ESLogServiceImpl implements LogService {

    private static final String SERVICE_NAME = "service-module-es";

    private static final String SERVICE_MAPPING = "/resource/v1/sync/log/create";

    @Resource(name = "lbWebClient")
    private WebClient webClient;

    @Override
    public void saveLog(LogInfo logInfo) {
        webClient.post().uri("http://".concat(SERVICE_NAME).concat(SERVICE_MAPPING))
                .body(Mono.just(logInfo),LogInfo.class)
                .retrieve()
                .bodyToMono(Result.class)
                .doOnError(WebClientResponseException.class, err -> {
                    log.error("保存请求log到ES中失败,status:{},msg:{}", err.getRawStatusCode(), err.getResponseBodyAsString());
                })
                .subscribe(e -> {
                    if (e.getCode() != ResultEnum.SUCCESS.getCode()) {
                        log.error("保存请求log到ES中失败，结果为: {}", JSONObject.toJSONString(e));
                    }else{
                        log.info("保存請求log到es , result = {} ",JSONObject.toJSONString(e));
                    }
                },err -> {
                    log.error("保存请求log到ES中失败 ",err);
                });

    }

}
