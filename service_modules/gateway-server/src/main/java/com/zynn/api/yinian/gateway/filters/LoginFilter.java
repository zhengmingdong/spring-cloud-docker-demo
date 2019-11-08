package com.zynn.api.yinian.gateway.filters;

import com.alibaba.fastjson.JSONObject;
import com.zynn.api.yinian.gateway.bean.LoginAndRegisterResult;
import com.zynn.api.yinian.gateway.constant.LoginUserConstant;
import com.zynn.api.yinian.gateway.constant.UrlConstant;
import com.zynn.api.yinian.gateway.result.Result;
import com.zynn.api.yinian.gateway.result.ResultEnum;
import com.zynn.api.yinian.gateway.result.ResultUtil;
import com.zynn.api.yinian.gateway.service.LogService;
import com.zynn.api.yinian.gateway.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

/**
 */
@Component
public class LoginFilter implements GlobalFilter, Ordered {

    @Autowired
    private ValueOperations valueOperations;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        ServerHttpRequest req = exchange.getRequest();
        String path = req.getURI().getRawPath();

        if (!UrlConstant.matchPath(UrlConstant.PASS_SERVLET_PATHS, path)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
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

                        byte[] uppedContent =  content;
                        if (StringUtils.isBlank(str)){
                            uppedContent = JSONObject.toJSONString(ResultUtil.fail(ResultEnum.SERVICE_ERROR)).getBytes();
                        }

                        final Result result = JSONObject.parseObject(str, Result.class);

                        if (result.getCode() == 0 || result.getCode() == 1002){
                            LoginAndRegisterResult user = JSONObject.parseObject(JSONObject.toJSONString(result.getData()), LoginAndRegisterResult.class);
                            if (Objects.nonNull(user)&&Objects.nonNull(user.getId()) && Objects.nonNull(user.getUserSex())) {
                                //除去登录接口,其他接口不返回app-user-info-key
                                String  md5Str = Optional.ofNullable(MD5Utils.md5Encrypt(user.getId())).orElse("");

                                exchange.getResponse().getHeaders().add(LoginUserConstant.APP_USER_INFO_KEY, md5Str);
                                valueOperations.set(md5Str,JSONObject.toJSONString(user));


                            }
                        }

                        return bufferFactory.wrap(uppedContent);
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
        return -10;
    }
}
