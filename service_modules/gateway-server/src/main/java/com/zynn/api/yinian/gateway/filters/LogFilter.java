package com.zynn.api.yinian.gateway.filters;

import com.alibaba.fastjson.JSONObject;
import com.zynn.api.yinian.gateway.bean.LogInfo;
import com.zynn.api.yinian.gateway.config.swagger.SwaggerProvider;
import com.zynn.api.yinian.gateway.constant.ProfilesConstant;
import com.zynn.api.yinian.gateway.result.Result;
import com.zynn.api.yinian.gateway.result.ResultEnum;
import com.zynn.api.yinian.gateway.service.LogService;
import com.zynn.api.yinian.gateway.utils.LogInfoUtil;
import com.zynn.api.yinian.gateway.utils.SendMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.Charset;

@Component
@Slf4j
public class LogFilter implements GlobalFilter, Ordered {

    @Value("${spring.profiles.active}")
    private String active;

    @Resource(name = "esLogService")
    private LogService logService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final long startTime = System.currentTimeMillis();
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        String path = exchange.getRequest().getURI().getRawPath();
        if (path.endsWith(SwaggerProvider.API_URI)){
            return chain.filter(exchange);
        }

        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        DataBufferUtils.release(dataBuffer);

                        String str = new String(content, Charset.forName("UTF-8"));

                        if(path.contains("/get/appDownload302")){
                            return bufferFactory.wrap(content);
                        }

                        final Result result = JSONObject.parseObject(str, Result.class);

                        final long endTime = System.currentTimeMillis();

                        final LogInfo logInfo = LogInfoUtil.logInfoForWeb(exchange);

                        logInfo.setResponseData(result.toString());
                        logInfo.setStartTime(startTime);
                        logInfo.setEndTime(endTime);
                        logInfo.setResponseStatusCode(result.getCode());
                        logService.saveLog(logInfo);

                        if (result.getCode() != ResultEnum.SUCCESS.getCode()){
                            final String msg = String.format("[接口报错]请求路径:%s,日志信息:%s", logInfo.getRequestUrl(), logInfo.toString());
                            log.error(msg);
                            if (active.equals(ProfilesConstant.PRO_ACTIVE)){
                                    SendMsgUtils.sendDingtalkMsg(msg);
                            }
                        }

                        if (active.equals(ProfilesConstant.PRO_ACTIVE)){
                            if ((endTime - startTime) >= 5000){
                                SendMsgUtils.sendDingtalkMsg(
                                        String.format("[接口超时]请求路径:%s,时间:%d,日志信息:%s",logInfo.getRequestUrl(),endTime - startTime,logInfo.toString()));
                            }
                        }

                        return bufferFactory.wrap(content);
                    }));
                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -3;
    }
}
